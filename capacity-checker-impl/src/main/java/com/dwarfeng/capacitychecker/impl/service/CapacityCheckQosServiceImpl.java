package com.dwarfeng.capacitychecker.impl.service;

import com.dwarfeng.capacitychecker.stack.bean.dto.CapacityCheckContext;
import com.dwarfeng.capacitychecker.stack.handler.CapacityCheckHandler;
import com.dwarfeng.capacitychecker.stack.service.CapacityCheckQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class CapacityCheckQosServiceImpl implements CapacityCheckQosService {

    private final CapacityCheckHandler capacityCheckHandler;

    private final ServiceExceptionMapper sem;

    public CapacityCheckQosServiceImpl(CapacityCheckHandler capacityCheckHandler, ServiceExceptionMapper sem) {
        this.capacityCheckHandler = capacityCheckHandler;
        this.sem = sem;
    }

    @Override
    public void online() throws ServiceException {
        try {
            capacityCheckHandler.online();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("上线容量检查处理器时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void offline() throws ServiceException {
        try {
            capacityCheckHandler.offline();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("下线容量检查处理器时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public CapacityCheckContext getLocalCache(LongIdKey sectionKey) throws ServiceException {
        try {
            return capacityCheckHandler.getLocalCache(sectionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("获取缓存的容量检查上下文时发生异常", LogLevel.WARN, sem, e);
        }
    }

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            capacityCheckHandler.clearLocalCache();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("清除本地缓存时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
