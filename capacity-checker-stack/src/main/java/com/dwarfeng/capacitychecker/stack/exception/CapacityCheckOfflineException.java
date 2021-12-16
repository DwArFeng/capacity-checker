package com.dwarfeng.capacitychecker.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 检查器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CapacityCheckOfflineException extends HandlerException {

    private static final long serialVersionUID = -2413541020898465165L;

    public CapacityCheckOfflineException() {
    }

    public CapacityCheckOfflineException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "容量检查服务已经被禁用";
    }
}
