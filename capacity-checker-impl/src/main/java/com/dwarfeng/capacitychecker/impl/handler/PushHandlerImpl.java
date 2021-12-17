package com.dwarfeng.capacitychecker.impl.handler;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.capacitychecker.stack.handler.PushHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class PushHandlerImpl implements PushHandler {

    private final List<Pusher> pushers;

    @Value("${pusher.type}")
    private String pusherType;

    private Pusher pusher;

    public PushHandlerImpl(@Autowired List<Pusher> pushers) {
        this.pushers = Objects.isNull(pushers) ? Collections.emptyList() : pushers;
    }

    @PostConstruct
    public void init() throws HandlerException {
        this.pusher = pushers.stream().filter(p -> p.supportType(pusherType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + pusherType));
    }

    @Override
    public void alarmInfoUpdated(AlarmInfo alarmInfo) throws HandlerException {
        pusher.alarmInfoUpdated(alarmInfo);
    }

    @Override
    public void checkHistoryRecorded(CheckHistory checkHistory) throws HandlerException {
        pusher.checkHistoryRecorded(checkHistory);
    }
}
