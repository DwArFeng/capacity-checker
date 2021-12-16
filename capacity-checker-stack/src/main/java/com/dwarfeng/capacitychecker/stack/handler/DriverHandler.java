package com.dwarfeng.capacitychecker.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 驱动处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriverHandler extends Handler {

    /**
     * 寻找指定的驱动器。
     *
     * @param type 驱动器的类型。
     * @return 符合驱动类型的指定的驱动器。
     * @throws HandlerException 处理器。
     */
    Driver find(String type) throws HandlerException;
}
