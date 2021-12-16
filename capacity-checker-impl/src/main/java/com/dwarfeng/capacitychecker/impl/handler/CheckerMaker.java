package com.dwarfeng.capacitychecker.impl.handler;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.exception.CheckerException;
import com.dwarfeng.capacitychecker.stack.handler.Checker;

/**
 * 检查器构造器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CheckerMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的检查器信息生成一个检查器对象。
     * <p>可以保证传入的检查器信息中的类型是支持的。</p>
     *
     * @param checkerInfo 指定的检查器信息。
     * @return 制造出的检查器。
     * @throws CheckerException 检查器异常。
     */
    Checker makeChecker(CheckerInfo checkerInfo) throws CheckerException;
}
