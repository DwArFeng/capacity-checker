package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 报警信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonAlarmInfo implements Bean {

    private static final long serialVersionUID = 8461873353671791232L;

    public static FastJsonAlarmInfo of(AlarmInfo alarmInfo) {
        if (Objects.isNull(alarmInfo)) {
            return null;
        } else {
            return new FastJsonAlarmInfo(
                    FastJsonLongIdKey.of(alarmInfo.getKey()), alarmInfo.getLimitCapacity(),
                    alarmInfo.getActualCapacity(), alarmInfo.getRatio(), alarmInfo.getHappenedDate(),
                    alarmInfo.getAlarmMessage(), alarmInfo.isAlarming(), alarmInfo.getCheckedDevice()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "limit_capacity", ordinal = 2)
    private long limitCapacity;

    @JSONField(name = "actual_capacity", ordinal = 3)
    private long actualCapacity;

    @JSONField(name = "ratio", ordinal = 4)
    private double ratio;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "alarm_message", ordinal = 6)
    private String alarmMessage;

    @JSONField(name = "alarming", ordinal = 7)
    private boolean alarming;

    @JSONField(name = "checked_device", ordinal = 8)
    private int checkedDevice;

    public FastJsonAlarmInfo() {
    }

    public FastJsonAlarmInfo(
            FastJsonLongIdKey key, long limitCapacity, long actualCapacity, double ratio, Date happenedDate,
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

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
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
        return "FastJsonAlarmInfo{" +
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
