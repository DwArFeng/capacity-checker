package com.dwarfeng.capacitychecker.stack.handler;

import com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo;
import com.dwarfeng.capacitychecker.stack.exception.DriverException;

/**
 * 驱动器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Driver {

    /**
     * 注册指定的驱动器信息。
     *
     * @param driverInfo 指定的驱动器信息。
     * @throws DriverException 驱动异常。
     */
    void register(DriverInfo driverInfo) throws DriverException;

    /**
     * 解除注册所有的驱动器信息。
     *
     * @throws DriverException 驱动异常。
     */
    void unregisterAll() throws DriverException;
}
