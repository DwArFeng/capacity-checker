package com.dwarfeng.capacitychecker.impl.handler.pusher;

import com.dwarfeng.capacitychecker.impl.handler.Pusher;
import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 丢弃掉部分消息的推送器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class PartialDrainPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "partial_drain";

    private final List<Pusher> pushers;

    @Value("${pusher.partial_drain.delegate_type}")
    private String delegateType;
    @Value("${pusher.partial_drain.drain_alarm_info_updated}")
    private boolean drainAlarmInfoUpdated;
    @Value("${pusher.partial_drain.drain_check_history_recorded}")
    private boolean drainCheckHistoryRecorded;

    private Pusher delegate;

    public PartialDrainPusher(@Autowired @Lazy List<Pusher> pushers) {
        super(PUSHER_TYPE);
        this.pushers = pushers;
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.delegate = pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType));
    }

    @Override
    public void alarmInfoUpdated(AlarmInfo alarmInfo) throws HandlerException {
        if (!drainAlarmInfoUpdated) {
            delegate.alarmInfoUpdated(alarmInfo);
        }
    }

    @Override
    public void checkHistoryRecorded(CheckHistory checkHistory) throws HandlerException {
        if (!drainCheckHistoryRecorded) {
            delegate.checkHistoryRecorded(checkHistory);
        }
    }

    @Override
    public String toString() {
        return "PartialDrainPusher{" +
                "delegateType='" + delegateType + '\'' +
                ", drainAlarmInfoUpdated=" + drainAlarmInfoUpdated +
                ", drainCheckHistoryRecorded=" + drainCheckHistoryRecorded +
                '}';
    }
}
