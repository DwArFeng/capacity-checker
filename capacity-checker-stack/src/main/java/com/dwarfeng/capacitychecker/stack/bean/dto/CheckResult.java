package com.dwarfeng.capacitychecker.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

/**
 * 检查结果。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CheckResult implements Dto {

    private static final long serialVersionUID = -1460423594120466330L;

    private long actualCapacity;

    public CheckResult() {
    }

    public CheckResult(long actualCapacity) {
        this.actualCapacity = actualCapacity;
    }

    public long getActualCapacity() {
        return actualCapacity;
    }

    public void setActualCapacity(long actualCapacity) {
        this.actualCapacity = actualCapacity;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "actualCapacity=" + actualCapacity +
                '}';
    }
}
