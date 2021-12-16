package com.dwarfeng.capacitychecker.impl.service;

import com.dwarfeng.capacitychecker.stack.handler.CapacityCheckHandler;
import com.dwarfeng.capacitychecker.stack.service.CapacityCheckService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class CapacityCheckServiceImpl implements CapacityCheckService {

    private final CapacityCheckHandler capacityCheckHandler;

    private final ServiceExceptionMapper sem;

    public CapacityCheckServiceImpl(CapacityCheckHandler capacityCheckHandler, ServiceExceptionMapper sem) {
        this.capacityCheckHandler = capacityCheckHandler;
        this.sem = sem;
    }

    @Override
    public void capacityCheck(LongIdKey sectionKey) throws ServiceException {
        try {
            capacityCheckHandler.capacityCheck(sectionKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("指定部件的容量检查时发生异常", LogLevel.WARN, sem, e);
        }
    }
}
