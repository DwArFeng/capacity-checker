package com.dwarfeng.capacitychecker.impl.bean.entity;

import com.dwarfeng.capacitychecker.sdk.bean.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_alarm_info")
public class HibernateAlarmInfo implements Bean {

    private static final long serialVersionUID = 7184336714662844590L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "limit_capacity", nullable = false)
    private long limitCapacity;

    @Column(name = "actual_capacity", nullable = false)
    private long actualCapacity;

    @Column(name = "ratio", nullable = false)
    private double ratio;

    @Column(name = "happened_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenedDate;

    @Column(name = "alarm_message", length = Constraints.LENGTH_ALARM_MESSAGE)
    private String alarmMessage;

    @Column(name = "alarming")
    private boolean alarming;

    // -----------------------------------------------------------一对一-----------------------------------------------------------
    @OneToOne(targetEntity = HibernateSection.class)
    @JoinColumns({ //
            @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateSection section;

    public HibernateAlarmInfo() {
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

    public HibernateSection getSection() {
        return section;
    }

    public void setSection(HibernateSection section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "longId = " + longId + ", " +
                "limitCapacity = " + limitCapacity + ", " +
                "actualCapacity = " + actualCapacity + ", " +
                "ratio = " + ratio + ", " +
                "happenedDate = " + happenedDate + ", " +
                "alarmMessage = " + alarmMessage + ", " +
                "alarming = " + alarming + ", " +
                "section = " + section + ")";
    }
}
