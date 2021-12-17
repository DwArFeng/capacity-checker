package com.dwarfeng.capacitychecker.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 报警信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class AlarmInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = 1433575906482170318L;

    private LongIdKey key;
    private long limitCapacity;
    private long actualCapacity;
    private double ratio;
    private Date happenedDate;
    private String alarmMessage;
    private boolean alarming;
    private int checkedDevice;

    public AlarmInfo() {
    }

    public AlarmInfo(
            LongIdKey key, long limitCapacity, long actualCapacity, double ratio, Date happenedDate,
            String alarmMessage, boolean alarming, int checkedDevice
    ) {
        this.key = key;
        this.limitCapacity = limitCapacity;
        this.actualCapacity = actualCapacity;
        this.ratio = ratio;
        this.happenedDate = happenedDate;
        this.alarmMessage = alarmMessage;
        this.alarming = alarming;
        this.checkedDevice = checkedDevice;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
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

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public boolean isAlarming() {
        return alarming;
    }

    public void setAlarming(boolean alarming) {
        this.alarming = alarming;
    }

    public int getCheckedDevice() {
        return checkedDevice;
    }

    public void setCheckedDevice(int checkedDevice) {
        this.checkedDevice = checkedDevice;
    }

    @Override
    public String toString() {
        return "AlarmInfo{" +
                "key=" + key +
                ", limitCapacity=" + limitCapacity +
                ", actualCapacity=" + actualCapacity +
                ", ratio=" + ratio +
                ", happenedDate=" + happenedDate +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarming=" + alarming +
                ", checkedDevice=" + checkedDevice +
                '}';
    }
}
