package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 检查历史。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonCheckHistory implements Bean {

    private static final long serialVersionUID = -4016387307327008749L;

    public FastJsonCheckHistory of(CheckHistory checkHistory) {
        if (Objects.isNull(checkHistory)) {
            return null;
        } else {
            return new FastJsonCheckHistory(
                    FastJsonLongIdKey.of(checkHistory.getKey()), FastJsonLongIdKey.of(checkHistory.getSectionKey()),
                    checkHistory.getLimitCapacity(), checkHistory.getActualCapacity(), checkHistory.getRatio(),
                    checkHistory.getHappenedDate()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "section_key", ordinal = 2)
    private FastJsonLongIdKey sectionKey;

    @JSONField(name = "limit_capacity", ordinal = 3)
    private long limitCapacity;

    @JSONField(name = "actual_capacity", ordinal = 4)
    private long actualCapacity;

    @JSONField(name = "ratio", ordinal = 5)
    private double ratio;

    @JSONField(name = "happened_date", ordinal = 6)
    private Date happenedDate;

    public FastJsonCheckHistory() {
    }

    public FastJsonCheckHistory(
            FastJsonLongIdKey key, FastJsonLongIdKey sectionKey, long limitCapacity, long actualCapacity,
            double ratio, Date happenedDate
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.limitCapacity = limitCapacity;
        this.actualCapacity = actualCapacity;
        this.ratio = ratio;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(FastJsonLongIdKey sectionKey) {
        this.sectionKey = sectionKey;
    }

    public long getLimitCapacity() {
        return limitCapacity;
    }

    public void setLimitCapacity(long limitCapacity) {
        this.limitCapacity = limitCapacity;
    }

    public long getActualCapacity() {
        return actualCapacity;
    }

    public void setActualCapacity(long actualCapacity) {
        this.actualCapacity = actualCapacity;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "FastJsonCheckHistory{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", limitCapacity=" + limitCapacity +
                ", actualCapacity=" + actualCapacity +
                ", ratio=" + ratio +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
