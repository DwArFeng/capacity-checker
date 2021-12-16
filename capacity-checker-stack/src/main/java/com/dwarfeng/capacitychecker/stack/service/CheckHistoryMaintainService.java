package com.dwarfeng.capacitychecker.stack.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 检查历史维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CheckHistoryMaintainService extends BatchCrudService<LongIdKey, CheckHistory>,
        EntireLookupService<CheckHistory>, PresetLookupService<CheckHistory> {

    String BETWEEN = "between";
    String CHILD_FOR_SECTION = "child_for_section";
    String CHILD_FOR_SECTION_BETWEEN = "child_for_section_between";
}
