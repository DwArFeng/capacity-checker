package com.dwarfeng.capacitychecker.stack.dao;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 检查历史数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CheckHistoryDao extends BatchBaseDao<LongIdKey, CheckHistory>, EntireLookupDao<CheckHistory>,
        PresetLookupDao<CheckHistory> {
}
