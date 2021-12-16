package com.dwarfeng.capacitychecker.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 检查历史。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CheckHistory implements Entity<LongIdKey> {

    private static final long serialVersionUID = -3068507861362140065L;

    private LongIdKey key;
    private LongIdKey sectionKey;
    private long limitCapacity;
    private long actualCapacity;
    private double ratio;
    private Date happenedDate;

    public CheckHistory() {
    }

    public CheckHistory(
            LongIdKey key, LongIdKey sectionKey, long limitCapacity, long actualCapacity, double ratio,
            Date happenedDate
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.limitCapacity = limitCapacity;
        this.actualCapacity = actualCapacity;
        this.ratio = ratio;
        this.happenedDate = happenedDate;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(LongIdKey sectionKey) {
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
        return "CheckHistory{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", limitCapacity=" + limitCapacity +
                ", actualCapacity=" + actualCapacity +
                ", ratio=" + ratio +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
