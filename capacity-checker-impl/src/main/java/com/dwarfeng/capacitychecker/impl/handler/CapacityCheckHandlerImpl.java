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
import com.dwarfeng.capacitychecker.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.capacitychecker.stack.service.CheckHistoryMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Lock lock = new ReentrantLock();
    private final Set<Driver> usedDrivers = new HashSet<>();
    private boolean onlineFlag = false;

    public CapacityCheckHandlerImpl(
            SectionMaintainService sectionMaintainService,
            CheckHistoryMaintainService checkHistoryMaintainService,
            AlarmInfoMaintainService alarmInfoMaintainService,
            LocalCacheHandler localCacheHandler
    ) {
        this.sectionMaintainService = sectionMaintainService;
        this.checkHistoryMaintainService = checkHistoryMaintainService;
        this.alarmInfoMaintainService = alarmInfoMaintainService;
        this.localCacheHandler = localCacheHandler;
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
            LOGGER.info("容量检查任务上线...");
            if (!onlineFlag) {
                // 获取所有启用的部件。
                List<Section> sections = sectionMaintainService.lookup(
                        SectionMaintainService.ENABLED, new Object[]{}
                ).getData();
                // 注册所有驱动成功标志。
                boolean successFlag = true;
                // 获取所有驱动信息。
                for (Section section : sections) {
                    CapacityCheckContext capacityCheckContext = localCacheHandler.getCapacityCheckContext(section.getKey());
                    if (Objects.isNull(capacityCheckContext)) {
                        throw new DriverException("无法在本地缓存中找到有效的驱动上下文: " + section.getKey());
                    }
                    if (!registerDriver(capacityCheckContext)) {
                        successFlag = false;
                    }
                }
                if (successFlag) {
                    LOGGER.info("所有驱动信息注册成功");
                } else {
                    LOGGER.warn("至少一条驱动信息注册失败，请查看警报日志以了解详细原因");
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
                LOGGER.warn("驱动信息 " + driverInfo + " 注册失败，将忽略此条注册信息", e);
            }
        }
        return successFlag;
    }

    @Override
    public void offline() throws HandlerException {
        lock.lock();
        try {
            if (onlineFlag) {
                LOGGER.info("解除注册所有驱动...");
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
    public CapacityCheckContext getLocalCache(LongIdKey sectionKey) throws HandlerException {
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
            // 判断是否允许记录，如果不允许，直接报错。
            if (!isOnline()) {
                throw new CapacityCheckOfflineException();
            }

            // 获取容量检查上下文。
            CapacityCheckContext capacityCheckContext = localCacheHandler.getCapacityCheckContext(sectionKey);
            if (Objects.isNull(capacityCheckContext)) {
                throw new SectionNotExistsException(sectionKey);
            }

            // 获取检查器，执行检查动作，获得检查结果，同时获取得出结果的时间。
            CheckResult checkResult = capacityCheckContext.getChecker().check();
            Date happenedDate = new Date();

            // 计算容量以及比例等参数，待报警分析时调用。
            long limitCapacity = capacityCheckContext.getSection().getLimitCapacity();
            long actualCapacity = checkResult.getActualCapacity();
            double ratio = (double) actualCapacity / limitCapacity;

            // 根据检查结果生成检查历史，并计入历史表。
            CheckHistory checkHistory = new CheckHistory(
                    null, sectionKey, limitCapacity, actualCapacity, ratio, happenedDate
            );
            checkHistoryMaintainService.insert(checkHistory);

            // 获取指定部件的所有报警设置。
            List<AlarmSetting> alarmSettings = capacityCheckContext.getAlarmSettings();

            // 遍历报警设置，查看是否有报警。
            boolean alarming = false;
            String alarmMessage = null;
            for (AlarmSetting alarmSetting : alarmSettings) {
                if (ratio >= alarmSetting.getRatioThreshold()) {
                    alarming = true;
                    alarmMessage = alarmSetting.getAlarmMessage();
                    break;
                }
            }

            // 更新报警信息。
            AlarmInfo alarmInfo = new AlarmInfo(
                    sectionKey, limitCapacity, actualCapacity, ratio, happenedDate, alarmMessage, alarming
            );
            alarmInfoMaintainService.insertOrUpdate(alarmInfo);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        } finally {
            lock.unlock();
        }
    }
}
