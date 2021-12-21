package com.dwarfeng.capacitychecker.impl.handler.checker;

import com.dwarfeng.capacitychecker.stack.bean.dto.CheckResult;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.exception.CheckerException;
import com.dwarfeng.capacitychecker.stack.exception.CheckerMakeException;
import com.dwarfeng.capacitychecker.stack.handler.Checker;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Groovy 检查器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class GroovyCheckerRegistry extends AbstractCheckerRegistry {

    public static final String CHECKER_TYPE = "groovy_checker";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyCheckerRegistry.class);

    private final ApplicationContext ctx;

    public GroovyCheckerRegistry(ApplicationContext ctx) {
        super(CHECKER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy判断器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的Groovy脚本对数据进行判断。";
    }

    @Override
    public String provideExampleContent() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExampleCheckerProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleFilterProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Checker makeChecker(CheckerInfo checkerInfo) throws CheckerException {
        try {
            // 通过Groovy脚本生成处理器。
            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class<?> aClass = classLoader.parseClass(checkerInfo.getContent());
            Processor processor = (Processor) aClass.newInstance();
            // 构建过滤器对象。
            GroovyChecker checker = ctx.getBean(GroovyChecker.class);
            checker.setCheckerInfoKey(checkerInfo.getKey());
            checker.setProcessor(processor);
            return checker;
        } catch (Exception e) {
            throw new CheckerMakeException(e, checkerInfo);
        }
    }

    @Override
    public String toString() {
        return "GroovyCheckerRegistry{" +
                "ctx=" + ctx +
                ", checkerType='" + checkerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyChecker implements Checker {

        private LongIdKey checkerInfoKey;
        private Processor processor;

        public GroovyChecker() {
        }

        @Override
        public CheckResult check() throws CheckerException {
            try {
                return processor.judge(checkerInfoKey);
            } catch (CheckerException e) {
                throw e;
            } catch (Exception e) {
                throw new CheckerException(e);
            }
        }

        public LongIdKey getCheckerInfoKey() {
            return checkerInfoKey;
        }

        public void setCheckerInfoKey(LongIdKey checkerInfoKey) {
            this.checkerInfoKey = checkerInfoKey;
        }

        public Processor getProcessor() {
            return processor;
        }

        public void setProcessor(Processor processor) {
            this.processor = processor;
        }

        @Override
        public String toString() {
            return "GroovyChecker{" +
                    "checkerInfoKey=" + checkerInfoKey +
                    ", processor=" + processor +
                    '}';
        }
    }

    /**
     * Groovy处理器。
     *
     * @author DwArFeng
     * @since 1.0.1
     */
    public interface Processor {

        /**
         * 对仓库处理器中的数据做出判断，并生成判断值。
         *
         * @param checkerInfoKey 判断器的主键。
         * @return 判断值。
         * @throws CheckerException 判断异常。
         */
        CheckResult judge(LongIdKey checkerInfoKey) throws CheckerException;
    }
}
