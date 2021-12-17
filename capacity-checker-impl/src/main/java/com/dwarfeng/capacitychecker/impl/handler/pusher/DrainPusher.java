package com.dwarfeng.capacitychecker.impl.handler.pusher;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import org.springframework.stereotype.Component;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "drain";

    public DrainPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void alarmInfoUpdated(AlarmInfo alarmInfo) {
    }

    @Override
    public void checkHistoryRecorded(CheckHistory checkHistory) {
    }

    @Override
    public String toString() {
        return "DrainPusher{" +
                "pusherType='" + pusherType + '\'' +
                '}';
    }
}
