package com.dwarfeng.capacitychecker.stack.dao;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;

/**
 * 检查器信息数据访问层。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CheckerInfoDao extends BatchBaseDao<LongIdKey, CheckerInfo>, EntireLookupDao<CheckerInfo> {
}
