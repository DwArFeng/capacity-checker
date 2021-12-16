package com.dwarfeng.capacitychecker.stack.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 警告设置维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmSettingMaintainService extends BatchCrudService<LongIdKey, AlarmSetting>,
        EntireLookupService<AlarmSetting>, PresetLookupService<AlarmSetting> {

    String CHILD_FOR_SECTION_THRESHOLD_DESC = "child_for_section_threshold_desc";
}
