package com.dwarfeng.capacitychecker.stack.handler;

import com.dwarfeng.capacitychecker.stack.bean.dto.CapacityCheckContext;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 容量检查处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface CapacityCheckHandler extends Handler {

    /**
     * 容量检查处理器是否上线。
     *
     * @return 是否上线。
     * @throws HandlerException 处理器异常。
     */
    boolean isOnline() throws HandlerException;

    /**
     * 上线容量检查处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void online() throws HandlerException;

    /**
     * 下线容量检查处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void offline() throws HandlerException;

    /**
     * 获取缓存的容量检查上下文。
     *
     * @param sectionKey 指定部件的主键。
     * @return 指定部件的容量检查上下文，或者是 null。
     * @throws HandlerException 处理器异常。
     */
    CapacityCheckContext getLocalCache(LongIdKey sectionKey) throws HandlerException;

    /**
     * 清除本地缓存。
     *
     * @throws HandlerException 处理器异常。
     */
    void clearLocalCache() throws HandlerException;

    /**
     * 指定部件的容量检查。
     *
     * @param sectionKey 指定的部件。
     * @throws HandlerException 处理器异常。
     */
    void capacityCheck(LongIdKey sectionKey) throws HandlerException;
}
