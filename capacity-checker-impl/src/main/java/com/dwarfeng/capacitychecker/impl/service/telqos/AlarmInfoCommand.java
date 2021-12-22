package com.dwarfeng.capacitychecker.impl.service.telqos;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class AlarmInfoCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoCommand.class);

    private static final String IDENTITY = "ai";
    private static final String DESCRIPTION = "报警信息查询";
    private static final String CMD_LINE_SYNTAX_I = "ai -i [-r rows] [-p page] [-h]";
    private static final String CMD_LINE_SYNTAX_S = "ai -s section-id [-h]";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_I + System.lineSeparator() + CMD_LINE_SYNTAX_S;

    private static final int DEFAULT_ROWS = 5;
    private static final int DEFAULT_PAGE = 0;

    private static final long HUMANIZE_KB = 1024;
    private static final long HUMANIZE_MB = HUMANIZE_KB * 1024;
    private static final long HUMANIZE_GB = HUMANIZE_MB * 1024;
    private static final long HUMANIZE_TB = HUMANIZE_GB * 1024;

    private final AlarmInfoMaintainService alarmInfoMaintainService;
    private final SectionMaintainService sectionMaintainService;

    public AlarmInfoCommand(
            AlarmInfoMaintainService alarmInfoMaintainService, SectionMaintainService sectionMaintainService
    ) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.alarmInfoMaintainService = alarmInfoMaintainService;
        this.sectionMaintainService = sectionMaintainService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("i").optionalArg(true).hasArg(false).desc("查看报警信息").build());
        list.add(Option.builder("h").optionalArg(true).hasArg(false).desc("人性化输出").build());
        list.add(Option.builder("r").optionalArg(true).hasArg(true).type(Number.class).argName("rows")
                .desc("每页显示的行数").build());
        list.add(Option.builder("p").optionalArg(true).hasArg(true).type(Number.class).argName("page")
                .desc("查询的页数").build());
        list.add(Option.builder("s").optionalArg(true).hasArg(true).type(Number.class).argName("section-id")
                .desc("查看具体某一个 section 的报警信息").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -i -s");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "i":
                    handleI(context, cmd);
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
        if (cmd.hasOption("i")) {
            i++;
            subCmd = "i";
        }
        if (cmd.hasOption("s")) {
            i++;
            subCmd = "s";
        }
        return Pair.of(subCmd, i);
    }

    private void handleI(Context context, CommandLine cmd) throws Exception {
        boolean humanize = cmd.hasOption("h");

        int rows = DEFAULT_ROWS;
        int page = DEFAULT_PAGE;
        try {
            if (cmd.hasOption("r")) {
                rows = ((Number) cmd.getParsedOptionValue("r")).intValue();
            }
            if (cmd.hasOption("p")) {
                page = ((Number) cmd.getParsedOptionValue("p")).intValue();
            }
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_I);
            context.sendMessage("请留意选项 s 后接参数的类型应该是数字 ");
            return;
        }

        List<AlarmInfo> alarmInfos = alarmInfoMaintainService.lookup(new PagingInfo(rows, page)).getData();
        printAlarmInfo(context, alarmInfos, rows * page, humanize);
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleS(Context context, CommandLine cmd) throws Exception {
        boolean humanize = cmd.hasOption("h");

        long sectionId;
        try {
            sectionId = ((Number) cmd.getParsedOptionValue("s")).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_S);
            context.sendMessage("请留意选项 s 后接参数的类型应该是数字 ");
            return;
        }

        AlarmInfo alarmInfo = alarmInfoMaintainService.getIfExists(new LongIdKey(sectionId));

        if (Objects.isNull(alarmInfo)) {
            context.sendMessage("指定的 section-id 不存在");
        } else {
            printAlarmInfo(context, Collections.singletonList(alarmInfo), 0, humanize);
        }
    }

    private void printAlarmInfo(Context context, List<AlarmInfo> alarmInfos, int startIndex, boolean humanize)
            throws Exception {
        if (alarmInfos.isEmpty()) {
            return;
        }

        // 根据 alarmInfo 查询对应的 section，并计算对应的容量。
        List<Section> sections = new ArrayList<>();
        List<String> limitCapacities = new ArrayList<>();
        List<String> actualCapacities = new ArrayList<>();
        for (AlarmInfo alarmInfo : alarmInfos) {
            sections.add(sectionMaintainService.get(alarmInfo.getKey()));
            limitCapacities.add(
                    humanize ? humanizeCapacity(alarmInfo.getLimitCapacity()) : alarmInfo.getLimitCapacity() + "B"
            );
            actualCapacities.add(
                    humanize ? humanizeCapacity(alarmInfo.getActualCapacity()) : alarmInfo.getActualCapacity() + "B"
            );
        }

        // 获取展示用的字符串的最大长度。
        int maxNameLength = sections.stream().map(s -> s.getName().length()).max(Integer::compareTo).get();
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        int maxLimitCapacityLength = limitCapacities.stream().map(String::length).max(Integer::compareTo).get();
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        int maxActualCapacityLength = actualCapacities.stream().map(String::length).max(Integer::compareTo).get();

        String format = "%1$-3d   name: %2$-" + (maxNameLength + 2) + "s limit-capacity: %3$-" +
                (maxLimitCapacityLength + 2) + "s actual-capacity: %4$-" + (maxActualCapacityLength + 2) +
                "s last-check-date:%5$tY-%5$tm-%5$td %5$tH:%5$tM:%5$tS   alarming: %6$-5b   alarm-message: %7$s";

        for (int i = 0; i < alarmInfos.size(); i++) {
            AlarmInfo alarmInfo = alarmInfos.get(i);
            Section section = sections.get(i);
            String limitCapacity = limitCapacities.get(i);
            String actualCapacity = actualCapacities.get(i);
            context.sendMessage(String.format(
                    format, startIndex + i, section.getName(), limitCapacity, actualCapacity,
                    alarmInfo.getHappenedDate(), alarmInfo.isAlarming(), alarmInfo.getAlarmMessage()
            ));
        }
    }

    private String humanizeCapacity(long capacity) {
        String result;
        DecimalFormat df = new DecimalFormat("#.00");
        if (capacity > HUMANIZE_TB) {
            result = df.format((double) capacity / HUMANIZE_TB) + "TB";
        } else if (capacity > HUMANIZE_GB) {
            result = df.format((double) capacity / HUMANIZE_GB) + "GB";
        } else if (capacity > HUMANIZE_MB) {
            result = df.format((double) capacity / HUMANIZE_MB) + "MB";
        } else if (capacity > HUMANIZE_KB) {
            result = df.format((double) capacity / HUMANIZE_KB) + "KB";
        } else {
            result = capacity + "B";
        }
        return result;
    }
}
