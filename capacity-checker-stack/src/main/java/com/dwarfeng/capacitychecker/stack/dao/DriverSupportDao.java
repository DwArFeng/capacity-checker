package com.dwarfeng.capacitychecker.stack.dao;

import com.dwarfeng.capacitychecker.stack.bean.entity.DriverSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 驱动器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface DriverSupportDao extends BaseDao<StringIdKey, DriverSupport>, EntireLookupDao<DriverSupport>,
        PresetLookupDao<DriverSupport> {
}
