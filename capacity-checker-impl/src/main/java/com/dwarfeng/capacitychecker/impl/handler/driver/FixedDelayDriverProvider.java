package com.dwarfeng.capacitychecker.impl.handler.driver;

import com.dwarfeng.capacitychecker.impl.handler.DriverProvider;
import com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo;
import com.dwarfeng.capacitychecker.stack.exception.DriverException;
import com.dwarfeng.capacitychecker.stack.handler.Driver;
import com.dwarfeng.capacitychecker.stack.service.CapacityCheckService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 固定间隔驱动提供器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class FixedDelayDriverProvider implements DriverProvider {

    public static final String SUPPORT_TYPE = "fixed_delay_driver";

    private final FixedDelayDriver fixedDelayDriver;

    public FixedDelayDriverProvider(@Lazy FixedDelayDriver fixedDelayDriver) {
        this.fixedDelayDriver = fixedDelayDriver;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Driver provide() {
        return fixedDelayDriver;
    }

    @Component
    public static class FixedDelayDriver implements Driver {

        private final ThreadPoolTaskScheduler scheduler;
        private final CapacityCheckService capacityCheckService;

        private final Lock lock = new ReentrantLock();
        private final Set<ScheduledFuture<?>> scheduledFutures = new HashSet<>();
        private final Set<FixedDelayProcessor> fixedDelayProcessors = new HashSet<>();

        public FixedDelayDriver(ThreadPoolTaskScheduler scheduler, CapacityCheckService capacityCheckService) {
            this.scheduler = scheduler;
            this.capacityCheckService = capacityCheckService;
        }

        @Override
        public void register(DriverInfo driverInfo) throws DriverException {
            lock.lock();
            try {
                LongIdKey sectionKey = driverInfo.getSectionKey();
                long delay = Long.parseLong(driverInfo.getContent());
                FixedDelayProcessor fixedDelayProcessor = new FixedDelayProcessor(
                        capacityCheckService,
                        sectionKey
                );
                ScheduledFuture<?> scheduledFuture =
                        scheduler.scheduleWithFixedDelay(fixedDelayProcessor, delay);
                fixedDelayProcessors.add(fixedDelayProcessor);
                scheduledFutures.add(scheduledFuture);
            } catch (Exception e) {
                throw new DriverException(e);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void unregisterAll() {
            lock.lock();
            try {
                for (ScheduledFuture<?> scheduledFuture : scheduledFutures) {
                    scheduledFuture.cancel(true);
                }
                for (FixedDelayProcessor fixedDelayProcessor : fixedDelayProcessors) {
                    fixedDelayProcessor.shutdown();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private static class FixedDelayProcessor implements Runnable {

        private static final Logger LOGGER = LoggerFactory.getLogger(FixedDelayProcessor.class);

        private final CapacityCheckService capacityCheckService;
        private final LongIdKey sectionKey;

        private final Lock lock = new ReentrantLock();
        private boolean runningFlag = true;

        private FixedDelayProcessor(CapacityCheckService capacityCheckService, LongIdKey sectionKey) {
            this.capacityCheckService = capacityCheckService;
            this.sectionKey = sectionKey;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                if (!runningFlag) {
                    return;
                }

                LOGGER.debug("计划时间已到达, fixed delay 驱动器驱动 " + sectionKey + " 部件执行容量检查动作...");
                capacityCheckService.capacityCheck(sectionKey);
            } catch (Exception e) {
                LOGGER.warn("记录 " + sectionKey + " 时出现异常, 放弃本次记录", e);
            } finally {
                lock.unlock();
            }
        }

        void shutdown() {
            lock.lock();
            try {
                runningFlag = false;
            } finally {
                lock.unlock();
            }
        }
    }
}
