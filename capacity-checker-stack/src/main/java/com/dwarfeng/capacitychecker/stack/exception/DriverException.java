package com.dwarfeng.capacitychecker.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 驱动器异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DriverException extends HandlerException {

    private static final long serialVersionUID = -5828403452732317127L;

    public DriverException() {
    }

    public DriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverException(String message) {
        super(message);
    }

    public DriverException(Throwable cause) {
        super(cause);
    }
}
