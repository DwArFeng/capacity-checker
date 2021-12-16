package com.dwarfeng.capacitychecker.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 部件不存在异常。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class SectionNotExistsException extends HandlerException {

    private static final long serialVersionUID = -8221283893016887589L;

    private final LongIdKey sectionKey;

    public SectionNotExistsException(LongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public SectionNotExistsException(Throwable cause, LongIdKey sectionKey) {
        super(cause);
        this.sectionKey = sectionKey;
    }

    @Override
    public String getMessage() {
        return "部件 " + sectionKey + " 不存在";
    }
}
