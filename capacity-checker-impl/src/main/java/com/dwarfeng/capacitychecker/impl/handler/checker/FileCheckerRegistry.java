package com.dwarfeng.capacitychecker.impl.handler.checker;

import com.dwarfeng.capacitychecker.stack.bean.dto.CheckResult;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.exception.CheckerException;
import com.dwarfeng.capacitychecker.stack.exception.CheckerMakeException;
import com.dwarfeng.capacitychecker.stack.handler.Checker;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 文件/文件夹大小检查器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class FileCheckerRegistry extends AbstractCheckerRegistry {

    public static final String CHECKER_TYPE = "file_checker";

    private final ApplicationContext ctx;

    public FileCheckerRegistry(ApplicationContext ctx) {
        super(CHECKER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "文件大小检查器";
    }

    @Override
    public String provideDescription() {
        return "监视一个固定的路径（可以是文件，也可以是目录）的大小。";
    }

    @Override
    public String provideExampleContent() {
        return "/var/log/cachk";
    }

    @Override
    public Checker makeChecker(CheckerInfo checkerInfo) throws CheckerException {
        try {
            FileChecker checker = ctx.getBean(FileChecker.class);
            checker.setPath(checkerInfo.getContent());
            return checker;
        } catch (Exception e) {
            throw new CheckerMakeException(e, checkerInfo);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class FileChecker implements Checker {

        private String path;

        public FileChecker() {
        }

        @Override
        public CheckResult check() throws CheckerException {
            try {
                long actualCapacity = FileUtils.sizeOf(new File(path));
                return new CheckResult(actualCapacity);
            } catch (Exception e) {
                throw new CheckerException(e);
            }
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
