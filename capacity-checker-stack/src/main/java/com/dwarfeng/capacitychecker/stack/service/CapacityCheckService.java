package com.dwarfeng.capacitychecker.stack.service;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 容量检查服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CapacityCheckService extends Service {

    /**
     * 指定部件的容量检查。
     *
     * @param sectionKey 指定的部件。
     * @throws ServiceException 服务异常。
     */
    void capacityCheck(LongIdKey sectionKey) throws ServiceException;
}
