package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 检查器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonCheckerInfo implements Bean {

    private static final long serialVersionUID = -4875256579655977638L;

    public static JSFixedFastJsonCheckerInfo of(CheckerInfo checkerInfo) {
        if (Objects.isNull(checkerInfo)) {
            return null;
        } else {
            return new JSFixedFastJsonCheckerInfo(
                    JSFixedFastJsonLongIdKey.of(checkerInfo.getKey()), checkerInfo.isEnabled(), checkerInfo.getType(),
                    checkerInfo.getContent(), checkerInfo.getRemark()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "enabled", ordinal = 2)
    private boolean enabled;

    @JSONField(name = "type", ordinal = 3)
    private String type;

    @JSONField(name = "content", ordinal = 4)
    private String content;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public JSFixedFastJsonCheckerInfo() {
    }

    public JSFixedFastJsonCheckerInfo(
            JSFixedFastJsonLongIdKey key, boolean enabled, String type, String content, String remark
    ) {
        this.key = key;
        this.enabled = enabled;
        this.type = type;
        this.content = content;
        this.remark = remark;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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
        return "JSFixedFastJsonCheckerInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
