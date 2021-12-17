package com.dwarfeng.capacitychecker.impl.handler;


import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 事件推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Pusher {

    boolean supportType(String type);

    void alarmInfoUpdated(AlarmInfo alarmInfo) throws HandlerException;

    void checkHistoryRecorded(CheckHistory checkHistory) throws HandlerException;
}
