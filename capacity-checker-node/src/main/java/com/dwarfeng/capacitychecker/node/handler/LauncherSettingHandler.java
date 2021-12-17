package com.dwarfeng.capacitychecker.node.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_driver_support}")
    private boolean resetDriverSupport;
    @Value("${launcher.reset_checker_support}")
    private boolean resetCheckerSupport;
    @Value("${launcher.start_capacity_check_delay}")
    private long startCapacityCheckDelay;

    public boolean isResetDriverSupport() {
        return resetDriverSupport;
    }

    public boolean isResetCheckerSupport() {
        return resetCheckerSupport;
    }

    public long getStartCapacityCheckDelay() {
        return startCapacityCheckDelay;
    }
}
