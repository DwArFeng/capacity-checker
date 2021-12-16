package com.dwarfeng.capacitychecker.impl.handler;

import com.dwarfeng.capacitychecker.stack.handler.Driver;

/**
 * 驱动器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriverProvider {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 提供驱动器。
     *
     * @return 被提供的驱动器。
     */
    Driver provide();
}
