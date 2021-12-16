package com.dwarfeng.capacitychecker.impl.configuration;

import com.dwarfeng.capacitychecker.impl.service.operation.SectionCrudOperation;
import com.dwarfeng.capacitychecker.stack.bean.entity.*;
import com.dwarfeng.capacitychecker.stack.dao.*;
import com.dwarfeng.sfds.api.integration.subgrade.SnowFlakeLongIdKeyFetcher;
import com.dwarfeng.subgrade.impl.bean.key.ExceptionKeyFetcher;
import com.dwarfeng.subgrade.impl.service.*;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    private final ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration;

    private final SectionCrudOperation sectionCrudOperation;
    private final SectionDao sectionDao;
    private final DriverInfoDao driverInfoDao;
    private final DriverSupportDao driverSupportDao;
    private final CheckerInfoDao checkerInfoDao;
    private final CheckerSupportDao checkerSupportDao;
    private final CheckHistoryDao checkHistoryDao;
    private final AlarmSettingDao alarmSettingDao;
    private final AlarmInfoDao alarmInfoDao;

    public ServiceConfiguration(
            ServiceExceptionMapperConfiguration serviceExceptionMapperConfiguration,
            SectionCrudOperation sectionCrudOperation, SectionDao sectionDao,
            DriverInfoDao driverInfoDao,
            DriverSupportDao driverSupportDao,
            CheckerInfoDao checkerInfoDao,
            CheckerSupportDao checkerSupportDao,
            CheckHistoryDao checkHistoryDao,
            AlarmSettingDao alarmSettingDao,
            AlarmInfoDao alarmInfoDao
    ) {
        this.serviceExceptionMapperConfiguration = serviceExceptionMapperConfiguration;
        this.sectionCrudOperation = sectionCrudOperation;
        this.sectionDao = sectionDao;
        this.driverInfoDao = driverInfoDao;
        this.driverSupportDao = driverSupportDao;
        this.checkerInfoDao = checkerInfoDao;
        this.checkerSupportDao = checkerSupportDao;
        this.checkHistoryDao = checkHistoryDao;
        this.alarmSettingDao = alarmSettingDao;
        this.alarmInfoDao = alarmInfoDao;
    }

    @Bean
    public KeyFetcher<LongIdKey> longIdKeyKeyFetcher() {
        return new SnowFlakeLongIdKeyFetcher();
    }

    @Bean
    public CustomBatchCrudService<LongIdKey, Section> sectionCustomBatchCrudService() {
        return new CustomBatchCrudService<>(
                sectionCrudOperation,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<Section> sectionDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                sectionDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<Section> sectionDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                sectionDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, DriverInfo> driverInfoDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                driverInfoDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<DriverInfo> driverInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                driverInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<DriverInfo> driverInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                driverInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyCrudService<StringIdKey, DriverSupport> driverSupportDaoOnlyCrudService() {
        return new DaoOnlyCrudService<>(
                driverSupportDao,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<DriverSupport> driverSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                driverSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<DriverSupport> driverSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                driverSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, CheckerInfo> checkerInfoDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                checkerInfoDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<CheckerInfo> checkerInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                checkerInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyCrudService<StringIdKey, CheckerSupport> checkerSupportDaoOnlyCrudService() {
        return new DaoOnlyCrudService<>(
                checkerSupportDao,
                new ExceptionKeyFetcher<>(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<CheckerSupport> checkerSupportDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                checkerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<CheckerSupport> checkerSupportDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                checkerSupportDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, CheckHistory> checkHistoryDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                checkHistoryDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<CheckHistory> checkHistoryDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                checkHistoryDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<CheckHistory> checkHistoryDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                checkHistoryDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, AlarmSetting> alarmSettingDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                alarmSettingDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmSetting> alarmSettingDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                alarmSettingDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmSetting> alarmSettingDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                alarmSettingDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyBatchCrudService<LongIdKey, AlarmInfo> alarmInfoDaoOnlyBatchCrudService() {
        return new DaoOnlyBatchCrudService<>(
                alarmInfoDao,
                longIdKeyKeyFetcher(),
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyEntireLookupService<AlarmInfo> alarmInfoDaoOnlyEntireLookupService() {
        return new DaoOnlyEntireLookupService<>(
                alarmInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }

    @Bean
    public DaoOnlyPresetLookupService<AlarmInfo> alarmInfoDaoOnlyPresetLookupService() {
        return new DaoOnlyPresetLookupService<>(
                alarmInfoDao,
                serviceExceptionMapperConfiguration.mapServiceExceptionMapper(),
                LogLevel.WARN
        );
    }
}
