package com.dwarfeng.capacitychecker.impl.service.operation;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.dao.*;
import com.dwarfeng.capacitychecker.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.capacitychecker.stack.service.DriverInfoMaintainService;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SectionCrudOperation implements BatchCrudOperation<LongIdKey, Section> {

    private final SectionDao sectionDao;
    private final DriverInfoDao driverInfoDao;
    private final CheckerInfoDao checkerInfoDao;
    private final AlarmSettingDao alarmSettingDao;
    private final AlarmInfoDao alarmInfoDao;

    public SectionCrudOperation(
            SectionDao sectionDao,
            DriverInfoDao driverInfoDao,
            CheckerInfoDao checkerInfoDao,
            AlarmSettingDao alarmSettingDao,
            AlarmInfoDao alarmInfoDao
    ) {
        this.sectionDao = sectionDao;
        this.driverInfoDao = driverInfoDao;
        this.checkerInfoDao = checkerInfoDao;
        this.alarmSettingDao = alarmSettingDao;
        this.alarmInfoDao = alarmInfoDao;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return sectionDao.exists(key);
    }

    @Override
    public Section get(LongIdKey key) throws Exception {
        return sectionDao.get(key);
    }

    @Override
    public LongIdKey insert(Section section) throws Exception {
        return sectionDao.insert(section);
    }

    @Override
    public void update(Section section) throws Exception {
        sectionDao.update(section);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        // ??????????????????????????????????????????
        List<LongIdKey> driverInfoKeys = driverInfoDao.lookup(DriverInfoMaintainService.CHILD_FOR_SECTION, new Object[]{key})
                .stream().map(DriverInfo::getKey).collect(Collectors.toList());
        driverInfoDao.batchDelete(driverInfoKeys);

        // ???????????????????????????????????????
        if (checkerInfoDao.exists(key)) {
            checkerInfoDao.delete(key);
        }

        // ???????????????????????????????????????
        List<LongIdKey> alarmSettingKeys = alarmSettingDao.lookup(
                AlarmSettingMaintainService.CHILD_FOR_SECTION_THRESHOLD_DESC, new Object[]{key}
        ).stream().map(AlarmSetting::getKey).collect(Collectors.toList());
        alarmSettingDao.batchDelete(alarmSettingKeys);

        // ???????????????????????????????????????
        if (alarmInfoDao.exists(key)) {
            alarmInfoDao.delete(key);
        }

        // ?????????????????????
        sectionDao.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return sectionDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return sectionDao.nonExists(keys);
    }

    @Override
    public List<Section> batchGet(List<LongIdKey> keys) throws Exception {
        return sectionDao.batchGet(keys);
    }

    @Override
    public List<LongIdKey> batchInsert(List<Section> sections) throws Exception {
        return sectionDao.batchInsert(sections);
    }

    @Override
    public void batchUpdate(List<Section> sections) throws Exception {
        sectionDao.batchUpdate(sections);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
