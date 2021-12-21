package com.dwarfeng.capacitychecker.stack.service;

import com.dwarfeng.capacitychecker.stack.bean.dto.CapacityCheckContext;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 容量检查 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CapacityCheckQosService extends Service {

    /**
     * 上线容量检查处理器。
     *
     * @throws ServiceException 服务异常。
     */
    void online() throws ServiceException;

    /**
     * 下线容量检查处理器。
     *
     * @throws ServiceException 服务异常。
     */
    void offline() throws ServiceException;

    /**
     * 获取缓存的容量检查上下文。
     *
     * @param sectionKey 指定部件的主键。
     * @return 指定部件的容量检查上下文，或者是 null。
     * @throws ServiceException 服务异常。
     */
    CapacityCheckContext getContext(LongIdKey sectionKey) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}
