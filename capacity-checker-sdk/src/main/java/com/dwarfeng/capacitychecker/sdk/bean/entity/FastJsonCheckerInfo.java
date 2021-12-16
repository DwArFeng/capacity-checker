package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 检查器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonCheckerInfo implements Bean {

    private static final long serialVersionUID = -1029027859955968388L;

    public static FastJsonCheckerInfo of(CheckerInfo checkerInfo) {
        if (Objects.isNull(checkerInfo)) {
            return null;
        } else {
            return new FastJsonCheckerInfo(
                    FastJsonLongIdKey.of(checkerInfo.getKey()), checkerInfo.isEnabled(), checkerInfo.getType(),
                    checkerInfo.getContent(), checkerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 3)
    private String type;

    @JSONField(name = "content", ordinal = 4)
    private String content;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public FastJsonCheckerInfo() {
    }

    public FastJsonCheckerInfo(FastJsonLongIdKey key, boolean enabled, String type, String content, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.type = type;
        this.content = content;
        this.remark = remark;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FastJsonCheckerInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
