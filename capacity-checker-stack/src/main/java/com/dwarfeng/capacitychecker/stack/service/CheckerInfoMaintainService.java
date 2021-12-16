package com.dwarfeng.capacitychecker.stack.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;

/**
 * 检查器信息维护服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CheckerInfoMaintainService extends BatchCrudService<LongIdKey, CheckerInfo>,
        EntireLookupService<CheckerInfo> {
}
