package com.dwarfeng.capacitychecker.impl.handler;

import com.dwarfeng.capacitychecker.stack.bean.dto.CapacityCheckContext;
import com.dwarfeng.capacitychecker.stack.bean.dto.CheckResult;
import com.dwarfeng.capacitychecker.stack.bean.entity.*;
import com.dwarfeng.capacitychecker.stack.exception.CapacityCheckOfflineException;
import com.dwarfeng.capacitychecker.stack.exception.DriverException;
import com.dwarfeng.capacitychecker.stack.exception.SectionNotExistsException;
import com.dwarfeng.capacitychecker.stack.handler.CapacityCheckHandler;
import com.dwarfeng.capacitychecker.stack.handler.Driver;
import com.dwarfeng.capacitychecker.stack.handler.LocalCacheHandler;
import com.dwarfeng.capacitychecker.stack.handler.PushHandler;
import com.dwarfeng.capacitychecker.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.capacitychecker.stack.service.CheckHistoryMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CapacityCheckHandlerImpl implements CapacityCheckHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CapacityCheckHandlerImpl.class);

    private final SectionMaintainService sectionMaintainService;
    private final CheckHistoryMaintainService checkHistoryMaintainService;
    private final AlarmInfoMaintainService alarmInfoMaintainService;

    private final LocalCacheHandler localCacheHandler;

    private final PushHandler pushHandler;

    @Value("${cachk.current_device}")
    private int currentDevice;

    private final Lock lock = new ReentrantLock();
    private final Set<Driver> usedDrivers = new HashSet<>();
    private boolean onlineFlag = false;

    public CapacityCheckHandlerImpl(
            SectionMaintainService sectionMaintainService,
            CheckHistoryMaintainService checkHistoryMaintainService,
            AlarmInfoMaintainService alarmInfoMaintainService,
            LocalCacheHandler localCacheHandler,
            PushHandler pushHandler
    ) {
        this.sectionMaintainService = sectionMaintainService;
        this.checkHistoryMaintainService = checkHistoryMaintainService;
        this.alarmInfoMaintainService = alarmInfoMaintainService;
        this.localCacheHandler = localCacheHandler;
        this.pushHandler = pushHandler;
    }

    @Override
    public boolean isOnline() {
        lock.lock();
        try {
            return onlineFlag;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void online() throws HandlerException {
        lock.lock();
        try {
            LOGGER.info("????????????????????????...");
            if (!onlineFlag) {
                // ??????????????????????????????
                List<Section> sections = sectionMaintainService.lookup(
                        SectionMaintainService.ENABLED_MATCH_DEVICE, new Object[]{currentDevice}
                ).getData();
                // ?????????????????????????????????
                boolean successFlag = true;
                // ???????????????????????????
                for (Section section : sections) {
                    CapacityCheckContext capacityCheckContext = localCacheHandler.getCapacityCheckContext(section.getKey());
                    if (Objects.isNull(capacityCheckContext)) {
                        throw new DriverException("??????????????????????????????????????????????????????: " + section.getKey());
                    }
                    if (!registerDriver(capacityCheckContext)) {
                        successFlag = false;
                    }
                }
                if (successFlag) {
                    LOGGER.info("??????????????????????????????");
                } else {
                    LOGGER.warn("?????????????????????????????????????????????????????????????????????????????????");
                }
                onlineFlag = true;
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    private boolean registerDriver(CapacityCheckContext capacityCheckContext) {
        boolean successFlag = true;
        Map<DriverInfo, Driver> driverMap = capacityCheckContext.getDriverMap();
        for (Map.Entry<DriverInfo, Driver> entry : driverMap.entrySet()) {
            DriverInfo driverInfo = entry.getKey();
            Driver driver = entry.getValue();
            try {
                driver.register(driverInfo);
                usedDrivers.add(driver);
            } catch (Exception e) {
                successFlag = false;
                LOGGER.warn("???????????? " + driverInfo + " ??????????????????????????????????????????", e);
            }
        }
        return successFlag;
    }

    @Override
    public void offline() throws HandlerException {
        lock.lock();
        try {
            if (onlineFlag) {
                LOGGER.info("????????????????????????...");
                for (Iterator<Driver> iterator = usedDrivers.iterator(); iterator.hasNext(); ) {
                    Driver driver = iterator.next();
                    driver.unregisterAll();
                    iterator.remove();
                }
                onlineFlag = false;
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public CapacityCheckContext getContext(LongIdKey sectionKey) throws HandlerException {
        lock.lock();
        try {
            return localCacheHandler.getCapacityCheckContext(sectionKey);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clearLocalCache() throws HandlerException {
        lock.lock();
        try {
            localCacheHandler.clear();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void capacityCheck(LongIdKey sectionKey) throws HandlerException {
        lock.lock();
        try {
            // ????????????????????????????????????????????????????????????
            if (!isOnline()) {
                throw new CapacityCheckOfflineException();
            }

            // ??????????????????????????????
            CapacityCheckContext capacityCheckContext = localCacheHandler.getCapacityCheckContext(sectionKey);
            if (Objects.isNull(capacityCheckContext)) {
                throw new SectionNotExistsException(sectionKey);
            }

            // ????????????????????????????????????????????????????????????????????????????????????????????????
            CheckResult checkResult = capacityCheckContext.getChecker().check();
            Date happenedDate = new Date();

            // ???????????????????????????????????????????????????????????????
            long limitCapacity = capacityCheckContext.getSection().getLimitCapacity();
            long actualCapacity = checkResult.getActualCapacity();
            double ratio = (double) actualCapacity / limitCapacity;

            // ????????????????????????????????????????????????????????????
            CheckHistory checkHistory = new CheckHistory(
                    null, sectionKey, limitCapacity, actualCapacity, ratio, happenedDate, currentDevice);
            checkHistoryMaintainService.insert(checkHistory);
            pushHandler.checkHistoryRecorded(checkHistory);

            // ??????????????????????????????????????????
            List<AlarmSetting> alarmSettings = capacityCheckContext.getAlarmSettings();

            // ?????????????????????????????????????????????
            boolean alarming = false;
            String alarmMessage = null;
            for (AlarmSetting alarmSetting : alarmSettings) {
                if (ratio >= alarmSetting.getRatioThreshold()) {
                    alarming = true;
                    alarmMessage = alarmSetting.getAlarmMessage();
                    break;
                }
            }

            // ?????????????????????
            AlarmInfo alarmInfo = new AlarmInfo(
                    sectionKey, limitCapacity, actualCapacity, ratio, happenedDate, alarmMessage, alarming,
                    currentDevice
            );
            alarmInfoMaintainService.insertOrUpdate(alarmInfo);
            pushHandler.alarmInfoUpdated(alarmInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }
}
