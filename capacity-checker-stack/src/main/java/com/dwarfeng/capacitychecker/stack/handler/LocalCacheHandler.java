package com.dwarfeng.capacitychecker.stack.handler;

import com.dwarfeng.capacitychecker.stack.bean.dto.CapacityCheckContext;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 本地缓存处理器。
 *
 * <p>处理器在本地保存数据，缓存中的数据可以保证与数据源保持同步。</p>
 * <p>数据存放在本地，必要时才与数据访问层通信，这有助于程序效率的提升。</p>
 * <p>该处理器线程安全。</p>
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface LocalCacheHandler extends Handler {

    /**
     * 获取指定部件的容量检查上下文。
     *
     * @param sectionKey 指定部件的主键。
     * @return 指定部件的指派上下文，或者是 null。
     * @throws HandlerException 处理器异常。
     */
    CapacityCheckContext getCapacityCheckContext(LongIdKey sectionKey) throws HandlerException;

    /**
     * 清除本地缓存。
     *
     * @throws HandlerException 处理器异常。
     */
    void clear() throws HandlerException;
}
