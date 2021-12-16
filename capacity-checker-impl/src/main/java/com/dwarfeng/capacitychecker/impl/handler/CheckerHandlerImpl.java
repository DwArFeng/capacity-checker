package com.dwarfeng.capacitychecker.impl.handler;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.exception.UnsupportedCheckerTypeException;
import com.dwarfeng.capacitychecker.stack.handler.Checker;
import com.dwarfeng.capacitychecker.stack.handler.CheckerHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CheckerHandlerImpl implements CheckerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckerHandlerImpl.class);

    private final List<CheckerMaker> checkerMakers;

    public CheckerHandlerImpl(List<CheckerMaker> checkerMakers) {
        this.checkerMakers = Objects.isNull(checkerMakers) ? new ArrayList<>() : checkerMakers;
    }

    @Override
    public Checker make(CheckerInfo checkerInfo) throws HandlerException {
        try {
            // 生成触发器。
            LOGGER.debug("通过检查器信息构建新的的检查器...");
            CheckerMaker checkerMaker = checkerMakers.stream().filter(maker -> maker.supportType(checkerInfo.getType()))
                    .findFirst().orElseThrow(() -> new UnsupportedCheckerTypeException(checkerInfo.getType()));
            Checker checker = checkerMaker.makeChecker(checkerInfo);
            LOGGER.debug("检查器构建成功!");
            LOGGER.debug("检查器: " + checker);
            return checker;
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}
