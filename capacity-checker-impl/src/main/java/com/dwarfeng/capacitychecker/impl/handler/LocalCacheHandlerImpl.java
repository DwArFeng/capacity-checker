package com.dwarfeng.capacitychecker.impl.handler;

import com.dwarfeng.capacitychecker.stack.bean.dto.CapacityCheckContext;
import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.handler.*;
import com.dwarfeng.capacitychecker.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.capacitychecker.stack.service.CheckerInfoMaintainService;
import com.dwarfeng.capacitychecker.stack.service.DriverInfoMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class LocalCacheHandlerImpl implements LocalCacheHandler {

    private final ContextFetcher contextFetcher;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<LongIdKey, CapacityCheckContext> contextMap = new HashMap<>();
    private final Set<LongIdKey> notExistSections = new HashSet<>();

    public LocalCacheHandlerImpl(ContextFetcher contextFetcher) {
        this.contextFetcher = contextFetcher;
    }

    @Override
    public CapacityCheckContext getCapacityCheckContext(LongIdKey sectionKey) throws HandlerException {
        try {
            lock.readLock().lock();
            try {
                if (contextMap.containsKey(sectionKey)) {
                    return contextMap.get(sectionKey);
                }
                if (notExistSections.contains(sectionKey)) {
                    return null;
                }
            } finally {
                lock.readLock().unlock();
            }
            lock.writeLock().lock();
            try {
                if (contextMap.containsKey(sectionKey)) {
                    return contextMap.get(sectionKey);
                }
                if (notExistSections.contains(sectionKey)) {
                    return null;
                }
                CapacityCheckContext capacityCheckContext = contextFetcher.fetchContext(sectionKey);
                if (Objects.nonNull(capacityCheckContext)) {
                    contextMap.put(sectionKey, capacityCheckContext);
                    return capacityCheckContext;
                }
                notExistSections.add(sectionKey);
                return null;
            } finally {
                lock.writeLock().unlock();
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            contextMap.clear();
            notExistSections.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Component
    public static class ContextFetcher {

        @Autowired
        private SectionMaintainService sectionMaintainService;
        @Autowired
        private DriverInfoMaintainService driverInfoMaintainService;
        @Autowired
        private CheckerInfoMaintainService checkerInfoMaintainService;
        @Autowired
        private AlarmSettingMaintainService alarmSettingMaintainService;

        @Autowired
        private DriverHandler driverHandler;
        @Autowired
        private CheckerHandler checkerHandler;

        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
        public CapacityCheckContext fetchContext(LongIdKey sectionKey) throws Exception {
            // ?????? Section ????????????????????? null???
            if (!sectionMaintainService.exists(sectionKey)) {
                return null;
            }
            Section section = sectionMaintainService.get(sectionKey);

            // ?????? Section ???????????????????????? null???
            if (!section.isEnabled()) {
                return null;
            }

            // ?????? Section ?????????????????? DriverInfo??????
            List<DriverInfo> driverInfos = driverInfoMaintainService.lookup(
                    DriverInfoMaintainService.CHILD_FOR_SECTION_ENABLED, new Object[]{sectionKey}
            ).getData();
            // ?????? driverInfos ???????????? 0???????????? null???
            if (driverInfos.isEmpty()) {
                return null;
            }

            // ?????? Section ????????? CheckerInfo???
            CheckerInfo checkerInfo = checkerInfoMaintainService.getIfExists(sectionKey);
            // ?????? checkerInfo ??? null???????????? null???
            if (Objects.isNull(checkerInfo)) {
                return null;
            }

            // ?????? DriverMap???
            Map<DriverInfo, Driver> driverMap = new HashMap<>();
            for (DriverInfo driverInfo : driverInfos) {
                driverMap.put(driverInfo, driverHandler.find(driverInfo.getType()));
            }

            // ?????? Checker???
            Checker checker = checkerHandler.make(checkerInfo);

            // ?????????????????????????????? AlarmSetting???
            List<AlarmSetting> alarmSettings = alarmSettingMaintainService.lookup(
                    AlarmSettingMaintainService.CHILD_FOR_SECTION_THRESHOLD_DESC, new Object[]{sectionKey}
            ).getData();

            return new CapacityCheckContext(section, driverMap, checkerInfo, checker, alarmSettings);
        }
    }
}
