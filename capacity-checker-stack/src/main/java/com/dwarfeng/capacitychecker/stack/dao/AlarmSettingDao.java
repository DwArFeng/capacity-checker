package com.dwarfeng.capacitychecker.stack.dao;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 警告设置数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmSettingDao extends BatchBaseDao<LongIdKey, AlarmSetting>, EntireLookupDao<AlarmSetting>,
        PresetLookupDao<AlarmSetting> {
}
