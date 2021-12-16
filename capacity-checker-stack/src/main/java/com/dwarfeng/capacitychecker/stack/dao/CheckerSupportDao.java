package com.dwarfeng.capacitychecker.stack.dao;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 检查器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CheckerSupportDao extends BaseDao<StringIdKey, CheckerSupport>, EntireLookupDao<CheckerSupport>,
        PresetLookupDao<CheckerSupport> {
}
