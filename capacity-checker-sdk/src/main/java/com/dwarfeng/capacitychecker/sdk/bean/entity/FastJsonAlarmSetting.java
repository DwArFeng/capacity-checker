package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 报警设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonAlarmSetting implements Bean {

    private static final long serialVersionUID = -2099160385315637472L;

    public static FastJsonAlarmSetting of(AlarmSetting alarmSetting) {
        if (Objects.isNull(alarmSetting)) {
            return null;
        } else {
            return new FastJsonAlarmSetting(
                    FastJsonLongIdKey.of(alarmSetting.getKey()), FastJsonLongIdKey.of(alarmSetting.getSectionKey()),
                    alarmSetting.getRatioThreshold(), alarmSetting.getAlarmMessage(), alarmSetting.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "sectionKey", ordinal = 2)
    private FastJsonLongIdKey sectionKey;

    @JSONField(name = "ratio_threshold", ordinal = 3)
    private double ratioThreshold;

    @JSONField(name = "alarm_message", ordinal = 4)
    private String alarmMessage;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonAlarmSetting() {
    }

    public FastJsonAlarmSetting(
            FastJsonLongIdKey key, FastJsonLongIdKey sectionKey, double ratioThreshold, String alarmMessage,
            String remark
    ) {
        this.key = key;
        this.sectionKey = sectionKey;
        this.ratioThreshold = ratioThreshold;
        this.alarmMessage = alarmMessage;
        this.remark = remark;
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
        return "FastJsonAlarmSetting{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", ratioThreshold=" + ratioThreshold +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
