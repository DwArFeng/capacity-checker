package com.dwarfeng.capacitychecker.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 检查器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CheckerException extends HandlerException {

    private static final long serialVersionUID = -526570370314933613L;

    public CheckerException() {
    }

    public CheckerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckerException(String message) {
        super(message);
    }

    public CheckerException(Throwable cause) {
        super(cause);
    }
}
