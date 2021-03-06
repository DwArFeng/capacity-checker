package com.dwarfeng.capacitychecker.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 检查器信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CheckerInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = -8911821387097749355L;

    private LongIdKey key;
    private boolean enabled;
    private String type;
    private String content;
    private String remark;

    public CheckerInfo() {
    }

    public CheckerInfo(LongIdKey key, boolean enabled, String type, String content, String remark) {
        this.key = key;
        this.enabled = enabled;
        this.type = type;
        this.content = content;
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
        return "CheckerInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
