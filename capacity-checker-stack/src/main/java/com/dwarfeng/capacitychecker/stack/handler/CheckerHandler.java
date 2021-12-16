package com.dwarfeng.capacitychecker.stack.handler;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 检查器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CheckerHandler extends Handler {

    /**
     * 根据指定的检查器信息构造一个检查器。
     *
     * @param checkerInfo 检查器信息。
     * @return 构造的检查器。
     * @throws HandlerException 处理器异常。
     */
    Checker make(CheckerInfo checkerInfo) throws HandlerException;
}
