package com.dwarfeng.capacitychecker.stack.exception;

/**
 * 不支持的检查器类型异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class UnsupportedCheckerTypeException extends CheckerException {

    private static final long serialVersionUID = -941857547647504798L;

    private final String type;

    public UnsupportedCheckerTypeException(String type) {
        this.type = type;
    }

    public UnsupportedCheckerTypeException(Throwable cause, String type) {
        super(cause);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "不支持的检查器类型: " + type;
    }
}
