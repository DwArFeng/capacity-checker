package com.dwarfeng.capacitychecker.impl.bean.entity;

import com.dwarfeng.capacitychecker.sdk.bean.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_section")
public class HibernateSection implements Bean {

    private static final long serialVersionUID = 1189294685141084586L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "limit_capacity", nullable = false)
    private long limitCapacity;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK)
    private String remark;

    // -----------------------------------------------------------一对一-----------------------------------------------------------
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = HibernateCheckerInfo.class, mappedBy = "section")
    private HibernateCheckerInfo workerInfo;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateDriverInfo.class, mappedBy = "section")
    private Set<HibernateDriverInfo> driverInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateAlarmSetting.class, mappedBy = "section")
    private Set<HibernateAlarmSetting> alarmSettings = new HashSet<>();

    public HibernateSection() {
    }

    // -----------------------------------------------------------映射用 getter&setter-----------------------------------------------------------
    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey idKey) {
        this.longId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    // -----------------------------------------------------------常规 getter&setter-----------------------------------------------------------
    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
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

    public HibernateCheckerInfo getWorkerInfo() {
        return workerInfo;
    }

    public void setWorkerInfo(HibernateCheckerInfo workerInfo) {
        this.workerInfo = workerInfo;
    }

    public Set<HibernateDriverInfo> getDriverInfos() {
        return driverInfos;
    }

    public void setDriverInfos(Set<HibernateDriverInfo> driverInfos) {
        this.driverInfos = driverInfos;
    }

    public Set<HibernateAlarmSetting> getAlarmSettings() {
        return alarmSettings;
    }

    public void setAlarmSettings(Set<HibernateAlarmSetting> alarmSettings) {
        this.alarmSettings = alarmSettings;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "name = " + name + ", " +
                "enabled = " + enabled + ", " +
                "limitCapacity = " + limitCapacity + ", " +
                "remark = " + remark + ", " +
                "workerInfo = " + workerInfo + ")";
    }
}
