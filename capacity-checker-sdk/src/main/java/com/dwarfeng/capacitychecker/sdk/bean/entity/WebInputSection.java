package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.sdk.bean.util.Constraints;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 * WebInput 部件。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputSection implements Bean {

    private static final long serialVersionUID = 465460206073425395L;

    public static Section toStackBean(WebInputSection webInputSection) {
        if (Objects.isNull(webInputSection)) {
            return null;
        }

        return new Section(
                WebInputLongIdKey.toStackBean(webInputSection.getKey()),
                webInputSection.getName(),
                webInputSection.isEnabled(),
                webInputSection.getLimitCapacity(),
                webInputSection.getRemark(),
                webInputSection.getRequiredDevice()
        );
    }

    @JSONField(name = "key")
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "limit_capacity")
    @PositiveOrZero
    private long limitCapacity;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    @JSONField(name = "required_device")
    @PositiveOrZero
    private Integer requiredDevice;

    public WebInputSection() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getLimitCapacity() {
        return limitCapacity;
    }

    public void setLimitCapacity(long limitCapacity) {
        this.limitCapacity = limitCapacity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRequiredDevice() {
        return requiredDevice;
    }

    public void setRequiredDevice(Integer requiredDevice) {
        this.requiredDevice = requiredDevice;
    }

    @Override
    public String toString() {
        return "WebInputSection{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", limitCapacity=" + limitCapacity +
                ", remark='" + remark + '\'' +
                ", requiredDevice=" + requiredDevice +
                '}';
    }
}
