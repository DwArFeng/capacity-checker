package com.dwarfeng.capacitychecker.stack.bean.dto;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.handler.Checker;
import com.dwarfeng.capacitychecker.stack.handler.Driver;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Map;

/**
 * 容量检查上下文。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CapacityCheckContext implements Dto {

    private static final long serialVersionUID = -7606379677459560998L;

    private Section section;
    private Map<DriverInfo, Driver> driverMap;
    private CheckerInfo checkerInfo;
    private Checker checker;
    private List<AlarmSetting> alarmSettings;

    public CapacityCheckContext() {
    }

    public CapacityCheckContext(
            Section section, Map<DriverInfo, Driver> driverMap, CheckerInfo checkerInfo, Checker checker,
            List<AlarmSetting> alarmSettings
    ) {
        this.section = section;
        this.driverMap = driverMap;
        this.checkerInfo = checkerInfo;
        this.checker = checker;
        this.alarmSettings = alarmSettings;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Map<DriverInfo, Driver> getDriverMap() {
        return driverMap;
    }

    public void setDriverMap(Map<DriverInfo, Driver> driverMap) {
        this.driverMap = driverMap;
    }

    public CheckerInfo getCheckerInfo() {
        return checkerInfo;
    }

    public void setCheckerInfo(CheckerInfo checkerInfo) {
        this.checkerInfo = checkerInfo;
    }

    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public List<AlarmSetting> getAlarmSettings() {
        return alarmSettings;
    }

    public void setAlarmSettings(List<AlarmSetting> alarmSettings) {
        this.alarmSettings = alarmSettings;
    }

    @Override
    public String toString() {
        return "CapacityCheckContext{" +
                "section=" + section +
                ", driverMap=" + driverMap +
                ", checkerInfo=" + checkerInfo +
                ", checker=" + checker +
                ", alarmSettings=" + alarmSettings +
                '}';
    }
}
