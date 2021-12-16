package com.dwarfeng.capacitychecker.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.capacitychecker.stack.bean.entity.DriverSupport;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson 驱动器支持。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonDriverSupport implements Bean {

    private static final long serialVersionUID = 870541875797116861L;

    public static FastJsonDriverSupport of(DriverSupport driverSupport) {
        if (Objects.isNull(driverSupport)) {
            return null;
        }
        return new FastJsonDriverSupport(
                FastJsonStringIdKey.of(driverSupport.getKey()),
                driverSupport.getLabel(),
                driverSupport.getDescription(),
                driverSupport.getExampleContent()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    @JSONField(name = "example_content", ordinal = 4)
    private String exampleContent;

    public FastJsonDriverSupport() {
    }

    public FastJsonDriverSupport(FastJsonStringIdKey key, String label, String description, String exampleContent) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.exampleContent = exampleContent;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExampleContent() {
        return exampleContent;
    }

    public void setExampleContent(String exampleContent) {
        this.exampleContent = exampleContent;
    }

    @Override
    public String toString() {
        return "FastJsonDriverSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleContent='" + exampleContent + '\'' +
                '}';
    }
}
