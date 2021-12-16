package com.dwarfeng.capacitychecker.stack.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 报警信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AlarmInfoMaintainService extends BatchCrudService<LongIdKey, AlarmInfo>,
        EntireLookupService<AlarmInfo>, PresetLookupService<AlarmInfo> {

    String ALARMING = "alarming";
}
