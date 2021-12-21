package com.dwarfeng.capacitychecker.impl.handler.checker;

import com.dwarfeng.capacitychecker.impl.handler.CheckerMaker;
import com.dwarfeng.capacitychecker.impl.handler.CheckerSupporter;

import java.util.Objects;

/**
 * 抽象检查器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractCheckerRegistry implements CheckerMaker, CheckerSupporter {

    protected String checkerType;

    public AbstractCheckerRegistry() {
    }

    public AbstractCheckerRegistry(String checkerType) {
        this.checkerType = checkerType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(checkerType, type);
    }

    @Override
    public String provideType() {
        return checkerType;
    }

    public String getCheckerType() {
        return checkerType;
    }

    public void setCheckerType(String checkerType) {
        this.checkerType = checkerType;
    }

    @Override
    public String toString() {
        return "AbstractCheckerRegistry{" +
                "checkerType='" + checkerType + '\'' +
                '}';
    }
}
