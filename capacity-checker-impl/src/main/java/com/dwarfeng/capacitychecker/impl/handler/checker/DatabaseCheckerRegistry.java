package com.dwarfeng.capacitychecker.impl.handler.checker;

import com.dwarfeng.capacitychecker.stack.bean.dto.CheckResult;
import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.exception.CheckerException;
import com.dwarfeng.capacitychecker.stack.exception.CheckerMakeException;
import com.dwarfeng.capacitychecker.stack.handler.Checker;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * 文件/文件夹大小检查器注册。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@Component
public class DatabaseCheckerRegistry extends AbstractCheckerRegistry implements EmbeddedValueResolverAware {

    public static final String CHECKER_TYPE = "database_checker";

    private final ApplicationContext ctx;

    private StringValueResolver resolver;

    public DatabaseCheckerRegistry(ApplicationContext ctx) {
        super(CHECKER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "数据库检查器";
    }

    @Override
    public String provideDescription() {
        return "根据指定的 settingId 获取数据库的连接信息，连接进数据库中后执行 setting 中配置好的 sql 语句。\n" +
                "SQL 语句的执行后需要返回一个含有 Long 类型的 ResultSet，" +
                "并且在 setting 中告知这个 Long 类型的值在 ResultSet 中的索引值。\n" +
                "获取这个 Long 值之后，将这个值当做当前容量。\n" +
                "安全起见，数据库只存放配置的 settingId，" +
                "settingId 对应的具体配置应该放在 conf/cachk/check.properties 中定义。";
    }

    @Override
    public String provideExampleContent() {
        return "your-setting-id-here";
    }

    @Override
    public Checker makeChecker(CheckerInfo checkerInfo) throws CheckerException {
        try {
            DatabaseChecker checker = ctx.getBean(DatabaseChecker.class);
            checker.setSettingId(checkerInfo.getContent());
            checker.setResolver(resolver);
            return checker;
        } catch (Exception e) {
            throw new CheckerMakeException(e, checkerInfo);
        }
    }

    @Override
    public void setEmbeddedValueResolver(@Nonnull StringValueResolver resolver) {
        this.resolver = resolver;
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class DatabaseChecker implements Checker {

        private static final String SETTING_NAME_JDBC_DRIVER = "jdbc.driver";
        private static final String SETTING_NAME_JDBC_URL = "jdbc.url";
        private static final String SETTING_NAME_JDBC_USER = "jdbc.user";
        private static final String SETTING_NAME_JDBC_PASSWORD = "jdbc.password";
        private static final String SETTING_NAME_JDBC_QUERY_SQL = "jdbc.query_sql";
        private static final String SETTING_NAME_JDBC_RESULT_INDEX = "jdbc.result_index";

        private static final String PLACEHOLDER_FORMAT = "${%s.%s}";

        private String settingId;
        private StringValueResolver resolver;

        public DatabaseChecker() {
        }

        @Override
        public CheckResult check() throws CheckerException {
            try {
                Config config = resolveConfig();
                String jdbcDriver = config.getJdbcDriver();
                String jdbcUrl = config.getJdbcUrl();
                String jdbcUser = config.getJdbcUser();
                String jdbcPassword = config.getJdbcPassword();
                String jdbcQuerySql = config.getJdbcQuerySql();
                int jdbcResultIndex = config.getJdbcResultIndex();

                DriverManagerDataSource ds = new DriverManagerDataSource();
                ds.setDriverClassName(jdbcDriver);
                ds.setUrl(jdbcUrl);
                ds.setUsername(jdbcUser);
                ds.setPassword(jdbcPassword);

                long result;

                try (Connection connection = ds.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(jdbcQuerySql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    result = resultSet.getLong(jdbcResultIndex);
                }

                return new CheckResult(result);
            } catch (Exception e) {
                throw new CheckerException(e);
            }
        }

        public String getSettingId() {
            return settingId;
        }

        public void setSettingId(String settingId) {
            this.settingId = settingId;
        }

        public StringValueResolver getResolver() {
            return resolver;
        }

        public void setResolver(StringValueResolver resolver) {
            this.resolver = resolver;
        }

        private Config resolveConfig() {
            String jdbcDriver = Objects.requireNonNull(resolver.resolveStringValue(
                    String.format(PLACEHOLDER_FORMAT, settingId, SETTING_NAME_JDBC_DRIVER)
            ));
            String jdbcUrl = Objects.requireNonNull(resolver.resolveStringValue(
                    String.format(PLACEHOLDER_FORMAT, settingId, SETTING_NAME_JDBC_URL)
            ));
            String jdbcUser = Objects.requireNonNull(resolver.resolveStringValue(
                    String.format(PLACEHOLDER_FORMAT, settingId, SETTING_NAME_JDBC_USER)
            ));
            String jdbcPassword = Objects.requireNonNull(resolver.resolveStringValue(
                    String.format(PLACEHOLDER_FORMAT, settingId, SETTING_NAME_JDBC_PASSWORD)
            ));
            String jdbcQuerySql = Objects.requireNonNull(resolver.resolveStringValue(
                    String.format(PLACEHOLDER_FORMAT, settingId, SETTING_NAME_JDBC_QUERY_SQL)
            ));
            String jdbcResultIndexString = Objects.requireNonNull(resolver.resolveStringValue(
                    String.format(PLACEHOLDER_FORMAT, settingId, SETTING_NAME_JDBC_RESULT_INDEX)
            ));

            int jdbcResultIndex = Integer.parseInt(jdbcResultIndexString);

            return new Config(jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword, jdbcQuerySql, jdbcResultIndex);
        }
    }

    private static final class Config {

        private final String jdbcDriver;
        private final String jdbcUrl;
        private final String jdbcUser;
        private final String jdbcPassword;
        private final String jdbcQuerySql;
        private final int jdbcResultIndex;

        public Config(
                String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPassword, String jdbcQuerySql,
                int jdbcResultIndex
        ) {
            this.jdbcDriver = jdbcDriver;
            this.jdbcUrl = jdbcUrl;
            this.jdbcUser = jdbcUser;
            this.jdbcPassword = jdbcPassword;
            this.jdbcQuerySql = jdbcQuerySql;
            this.jdbcResultIndex = jdbcResultIndex;
        }

        public String getJdbcDriver() {
            return jdbcDriver;
        }

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public String getJdbcUser() {
            return jdbcUser;
        }

        public String getJdbcPassword() {
            return jdbcPassword;
        }

        public String getJdbcQuerySql() {
            return jdbcQuerySql;
        }

        public int getJdbcResultIndex() {
            return jdbcResultIndex;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "jdbcDriver='" + jdbcDriver + '\'' +
                    ", jdbcUrl='" + jdbcUrl + '\'' +
                    ", jdbcUser='" + jdbcUser + '\'' +
                    ", jdbcPassword='" + jdbcPassword + '\'' +
                    ", jdbcQuerySql='" + jdbcQuerySql + '\'' +
                    ", jdbcResultIndex='" + jdbcResultIndex + '\'' +
                    '}';
        }
    }
}
