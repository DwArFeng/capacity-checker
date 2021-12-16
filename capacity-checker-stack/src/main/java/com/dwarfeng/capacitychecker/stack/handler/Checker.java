package com.dwarfeng.capacitychecker.stack.handler;

import com.dwarfeng.capacitychecker.stack.bean.dto.CheckResult;
import com.dwarfeng.capacitychecker.stack.exception.CheckerException;

/**
 * 检查器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Checker {

    /**
     * 对仓库处理器中的数据做出判断，并生成判断值。
     *
     * @return 检查结果。
     * @throws CheckerException 检查器异常。
     */
    CheckResult check() throws CheckerException;
}
