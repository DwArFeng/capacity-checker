package com.dwarfeng.capacitychecker.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 报警设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class AlarmSetting implements Entity<LongIdKey> {

    private static final long serialVersionUID = 769800503385129139L;

    private LongIdKey key;
    private LongIdKey sectionKey;
    private double ratioThreshold;
    private String alarmMessage;
    private String remark;

    public AlarmSetting() {
    }

    public AlarmSetting(LongIdKey key, LongIdKey sectionKey, double ratioThreshold, String alarmMessage, String remark) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.ratioThreshold = ratioThreshold;
        this.alarmMessage = alarmMessage;
        this.remark = remark;
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

    public double getRatioThreshold() {
        return ratioThreshold;
    }

    public void setRatioThreshold(double ratioThreshold) {
        this.ratioThreshold = ratioThreshold;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AlarmSetting{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", ratioThreshold=" + ratioThreshold +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
