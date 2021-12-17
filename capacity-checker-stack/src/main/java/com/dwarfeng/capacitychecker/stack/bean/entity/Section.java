package com.dwarfeng.capacitychecker.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 部件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class Section implements Entity<LongIdKey> {

    private static final long serialVersionUID = -8694375547325180271L;

    private LongIdKey key;
    private String name;
    private boolean enabled;
    private long limitCapacity;
    private String remark;
    private Integer requiredDevice;

    public Section() {
    }

    public Section(
            LongIdKey key, String name, boolean enabled, long limitCapacity, String remark, Integer requiredDevice
    ) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.limitCapacity = limitCapacity;
        this.remark = remark;
        this.requiredDevice = requiredDevice;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getLimitCapacity() {
        return limitCapacity;
    }

    public void setLimitCapacity(long limitCapacity) {
        this.limitCapacity = limitCapacity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRequiredDevice() {
        return requiredDevice;
    }

    public void setRequiredDevice(Integer requiredDevice) {
        this.requiredDevice = requiredDevice;
    }

    @Override
    public String toString() {
        return "Section{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", limitCapacity=" + limitCapacity +
                ", remark='" + remark + '\'' +
                ", requiredDevice=" + requiredDevice +
                '}';
    }
}
