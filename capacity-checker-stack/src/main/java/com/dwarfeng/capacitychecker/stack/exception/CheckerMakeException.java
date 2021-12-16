package com.dwarfeng.capacitychecker.stack.exception;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;

/**
 * 检查器构造异常。
 *
 * @author DwArFeng
 * @since beta-1.0.0
 */
public class CheckerMakeException extends CheckerException {

    private static final long serialVersionUID = -2758074789975975652L;

    private final CheckerInfo checkerInfo;

    public CheckerMakeException(CheckerInfo checkerInfo) {
        this.checkerInfo = checkerInfo;
    }

    public CheckerMakeException(Throwable cause, CheckerInfo checkerInfo) {
        super(cause);
        this.checkerInfo = checkerInfo;
    }

    @Override
    public String getMessage() {
        return "检查器构造失败, 相关的检查器信息为: " + checkerInfo;
    }
}
