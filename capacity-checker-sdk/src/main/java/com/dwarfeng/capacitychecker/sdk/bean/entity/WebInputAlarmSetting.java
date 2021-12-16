package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.sdk.bean.util.Constraints;
import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * WebInput 报警设置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputAlarmSetting implements Bean {

    private static final long serialVersionUID = 1784052010093354724L;

    public static AlarmSetting toStackBean(WebInputAlarmSetting webInputAlarmSetting) {
        if (Objects.isNull(webInputAlarmSetting)) {
            return null;
        } else {
            return new AlarmSetting(
                    WebInputLongIdKey.toStackBean(webInputAlarmSetting.getKey()),
                    WebInputLongIdKey.toStackBean(webInputAlarmSetting.getSectionKey()),
                    webInputAlarmSetting.getRatioThreshold(), webInputAlarmSetting.getAlarmMessage(),
                    webInputAlarmSetting.getRemark()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "section_key")
    @Valid
    private WebInputLongIdKey sectionKey;

    @JSONField(name = "ratio_threshold")
    @PositiveOrZero
    private double ratioThreshold;

    @JSONField(name = "alarm_message")
    @Length(max = Constraints.LENGTH_ALARM_MESSAGE)
    private String alarmMessage;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputAlarmSetting() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(WebInputLongIdKey sectionKey) {
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
        return "WebInputAlarmSetting{" +
                "key=" + key +
                ", sectionKey=" + sectionKey +
                ", ratioThreshold=" + ratioThreshold +
                ", alarmMessage='" + alarmMessage + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
