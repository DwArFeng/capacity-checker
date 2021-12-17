package com.dwarfeng.capacitychecker.node.launcher;

import com.dwarfeng.capacitychecker.node.handler.LauncherSettingHandler;
import com.dwarfeng.capacitychecker.stack.service.CapacityCheckQosService;
import com.dwarfeng.capacitychecker.stack.service.CheckerSupportMaintainService;
import com.dwarfeng.capacitychecker.stack.service.DriverSupportMaintainService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);
            //判断是否重置检查器和驱动器。
            if (launcherSettingHandler.isResetDriverSupport()) {
                LOGGER.info("重置驱动器支持...");
                DriverSupportMaintainService maintainService = ctx.getBean(DriverSupportMaintainService.class);
                try {
                    maintainService.reset();
                } catch (ServiceException e) {
                    LOGGER.warn("驱动器支持重置失败，异常信息如下", e);
                }
            }
            if (launcherSettingHandler.isResetCheckerSupport()) {
                LOGGER.info("重置检查器支持...");
                CheckerSupportMaintainService maintainService = ctx.getBean(CheckerSupportMaintainService.class);
                try {
                    maintainService.reset();
                } catch (ServiceException e) {
                    LOGGER.warn("检查器支持重置失败，异常信息如下", e);
                }
            }
            // 判断是否开启容量检查。
            long startCapacityCheckDelay = launcherSettingHandler.getStartCapacityCheckDelay();
            CapacityCheckQosService capacityCheckQosService = ctx.getBean(CapacityCheckQosService.class);
            if (startCapacityCheckDelay == 0) {
                LOGGER.info("立即启动容量检查服务...");
                try {
                    capacityCheckQosService.online();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动容量检查服务，异常原因如下", e);
                }
            } else if (startCapacityCheckDelay > 0) {
                LOGGER.info(startCapacityCheckDelay + " 毫秒后启动容量检查服务...");
                try {
                    Thread.sleep(startCapacityCheckDelay);
                } catch (InterruptedException ignored) {
                }
                LOGGER.info("启动容量检查服务...");
                try {
                    capacityCheckQosService.online();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动容量检查服务，异常原因如下", e);
                }
            }
        });
    }
}
