package com.dwarfeng.capacitychecker.impl.service.telqos;

import com.dwarfeng.capacitychecker.stack.bean.dto.CapacityCheckContext;
import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo;
import com.dwarfeng.capacitychecker.stack.handler.Checker;
import com.dwarfeng.capacitychecker.stack.handler.Driver;
import com.dwarfeng.capacitychecker.stack.service.CapacityCheckQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CheckLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLocalCacheCommand.class);

    private static final String IDENTITY = "clc";
    private static final String DESCRIPTION = "本地缓存操作";
    private static final String CMD_LINE_SYNTAX_C = "clc -c";
    private static final String CMD_LINE_SYNTAX_S = "clc -s section-id";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_C + System.lineSeparator() + CMD_LINE_SYNTAX_S;

    private final CapacityCheckQosService capacityCheckQosService;

    public CheckLocalCacheCommand(CapacityCheckQosService capacityCheckQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.capacityCheckQosService = capacityCheckQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("c").optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(Option.builder("s").optionalArg(true).hasArg(true).type(Number.class).argName("section-id")
                .desc("查看指定部件的详细信息，如果本地缓存中不存在，则尝试抓取").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -c -s");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "c":
                    handleC(context);
                    break;
                case "s":
                    handleS(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    public Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("c")) {
            i++;
            subCmd = "c";
        }
        if (cmd.hasOption("s")) {
            i++;
            subCmd = "s";
        }
        return Pair.of(subCmd, i);
    }

    private void handleC(Context context) throws Exception {
        capacityCheckQosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleS(Context context, CommandLine cmd) throws Exception {
        long sectionId;
        try {
            sectionId = ((Number) cmd.getParsedOptionValue("s")).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_S);
            context.sendMessage("请留意选项 s 后接参数的类型应该是数字 ");
            return;
        }
        CapacityCheckContext capacityCheckContext = capacityCheckQosService.getContext(new LongIdKey(sectionId));
        if (Objects.isNull(capacityCheckContext)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("section: %s", capacityCheckContext.getSection().toString()));

        context.sendMessage("");
        context.sendMessage("checker:");
        CheckerInfo checkerInfo = capacityCheckContext.getCheckerInfo();
        Checker checker = capacityCheckContext.getChecker();
        context.sendMessage(String.format("  %s", checkerInfo.toString()));
        context.sendMessage(String.format("  %s", checker.toString()));

        context.sendMessage("");
        context.sendMessage("drivers:");
        int index = 0;
        for (Map.Entry<DriverInfo, Driver> entry : capacityCheckContext.getDriverMap().entrySet()) {
            if (index != 0) {
                context.sendMessage("");
            }
            context.sendMessage(String.format("  %-3d %s", ++index, entry.getKey().toString()));
            context.sendMessage(String.format("  %-3d %s", index, entry.getValue().toString()));
        }

        context.sendMessage("");
        context.sendMessage("alarmSettings:");
        index = 0;
        for (AlarmSetting alarmSetting : capacityCheckContext.getAlarmSettings()) {
            if (index != 0) {
                context.sendMessage("");
            }
            context.sendMessage(String.format("  %-3d %s", ++index, alarmSetting.toString()));
        }
    }
}
