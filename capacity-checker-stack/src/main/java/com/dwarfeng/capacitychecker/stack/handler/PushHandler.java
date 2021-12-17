package com.dwarfeng.capacitychecker.stack.handler;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 推送器处理器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PushHandler extends Handler {

    void alarmInfoUpdated(AlarmInfo alarmInfo) throws HandlerException;

    void checkHistoryRecorded(CheckHistory checkHistory) throws HandlerException;
}
