package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 部件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonSection implements Bean {

    private static final long serialVersionUID = 2754727304579892968L;

    public static FastJsonSection of(Section section) {
        if (Objects.isNull(section)) {
            return null;
        }
        return new FastJsonSection(
                FastJsonLongIdKey.of(section.getKey()), section.getName(), section.isEnabled(),
                section.getLimitCapacity(), section.getRemark()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "limit_capacity", ordinal = 4)
    private long limitCapacity;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonSection() {
    }

    public FastJsonSection(FastJsonLongIdKey key, String name, boolean enabled, long limitCapacity, String remark) {
        this.key = key;
        this.name = name;
        this.enabled = enabled;
        this.limitCapacity = limitCapacity;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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

    @Override
    public String toString() {
        return "FastJsonSection{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", limitCapacity=" + limitCapacity +
                ", remark='" + remark + '\'' +
                '}';
    }
}
