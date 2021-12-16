package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 报警信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonAlarmInfo implements Bean {

    private static final long serialVersionUID = -8589441877555340295L;

    public static JSFixedFastJsonAlarmInfo of(AlarmInfo alarmInfo) {
        if (Objects.isNull(alarmInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonAlarmInfo(
                    JSFixedFastJsonLongIdKey.of(alarmInfo.getKey()), alarmInfo.getLimitCapacity(),
                    alarmInfo.getActualCapacity(), alarmInfo.getRatio(), alarmInfo.getHappenedDate(),
                    alarmInfo.getAlarmMessage(), alarmInfo.isAlarming()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "limit_capacity", ordinal = 2, serializeUsing = ToStringSerializer.class)
    private long limitCapacity;

    @JSONField(name = "actual_capacity", ordinal = 3, serializeUsing = ToStringSerializer.class)
    private long actualCapacity;

    @JSONField(name = "ratio", ordinal = 4)
    private double ratio;

    @JSONField(name = "happened_date", ordinal = 5)
    private Date happenedDate;

    @JSONField(name = "alarm_message", ordinal = 6)
    private String alarmMessage;

    @JSONField(name = "alarming", ordinal = 7)
    private boolean alarming;

    public JSFixedFastJsonAlarmInfo() {
    }

    public JSFixedFastJsonAlarmInfo(
            JSFixedFastJsonLongIdKey key, long limitCapacity, long actualCapacity, double ratio, Date happenedDate,
            String alarmMessage, boolean alarming
    ) {
        this.key = key;
        this.limitCapacity = limitCapacity;
        this.actualCapacity = actualCapacity;
        this.ratio = ratio;
        this.happenedDate = happenedDate;
        this.alarmMessage = alarmMessage;
        this.alarming = alarming;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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

    @Override
    public String toString() {
        return "JSFixedFastJsonAlarmInfo{" +
                "key=" + key +
                ", limitCapacity=" + limitCapacity +
                ", actualCapacity=" + actualCapacity +
                ", ratio=" + ratio +
                ", happenedDate=" + happenedDate +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", alarming=" + alarming +
                '}';
    }
}
