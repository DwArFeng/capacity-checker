package com.dwarfeng.capacitychecker.impl.configuration;

import com.dwarfeng.capacitychecker.impl.bean.entity.*;
import com.dwarfeng.capacitychecker.impl.dao.preset.*;
import com.dwarfeng.capacitychecker.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.impl.dao.HibernateBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.sdk.hibernate.modification.DefaultDeletionMod;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    private final HibernateTemplate hibernateTemplate;
    private final Mapper mapper;

    private final SectionPresetCriteriaMaker sectionPresetCriteriaMaker;
    private final DriverInfoPresetCriteriaMaker driverInfoPresetCriteriaMaker;
    private final DriverSupportPresetCriteriaMaker driverSupportPresetCriteriaMaker;
    private final CheckerSupportPresetCriteriaMaker checkerSupportPresetCriteriaMaker;
    private final CheckHistoryPresetCriteriaMaker checkHistoryPresetCriteriaMaker;
    private final AlarmSettingPresetCriteriaMaker alarmSettingPresetCriteriaMaker;
    private final AlarmInfoPresetCriteriaMaker alarmInfoPresetCriteriaMaker;

    @Value("${hibernate.jdbc.batch_size}")
    private int batchSize;

    public DaoConfiguration(
            HibernateTemplate hibernateTemplate, Mapper mapper,
            SectionPresetCriteriaMaker sectionPresetCriteriaMaker,
            DriverInfoPresetCriteriaMaker driverInfoPresetCriteriaMaker,
            DriverSupportPresetCriteriaMaker driverSupportPresetCriteriaMaker,
            CheckerSupportPresetCriteriaMaker checkerSupportPresetCriteriaMaker,
            CheckHistoryPresetCriteriaMaker checkHistoryPresetCriteriaMaker,
            AlarmSettingPresetCriteriaMaker alarmSettingPresetCriteriaMaker,
            AlarmInfoPresetCriteriaMaker alarmInfoPresetCriteriaMaker
    ) {
        this.hibernateTemplate = hibernateTemplate;
        this.mapper = mapper;
        this.sectionPresetCriteriaMaker = sectionPresetCriteriaMaker;
        this.driverInfoPresetCriteriaMaker = driverInfoPresetCriteriaMaker;
        this.driverSupportPresetCriteriaMaker = driverSupportPresetCriteriaMaker;
        this.checkerSupportPresetCriteriaMaker = checkerSupportPresetCriteriaMaker;
        this.checkHistoryPresetCriteriaMaker = checkHistoryPresetCriteriaMaker;
        this.alarmSettingPresetCriteriaMaker = alarmSettingPresetCriteriaMaker;
        this.alarmInfoPresetCriteriaMaker = alarmInfoPresetCriteriaMaker;
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, Section, HibernateSection>
    sectionHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(Section.class, HibernateSection.class, mapper),
                HibernateSection.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<Section, HibernateSection> sectionHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Section.class, HibernateSection.class, mapper),
                HibernateSection.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Section, HibernateSection> sectionHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(Section.class, HibernateSection.class, mapper),
                HibernateSection.class,
                sectionPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, DriverInfo, HibernateDriverInfo>
    driverInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, mapper),
                HibernateDriverInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernatePresetLookupDao<DriverInfo, HibernateDriverInfo> driverInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, mapper),
                HibernateDriverInfo.class,
                driverInfoPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, CheckerInfo, HibernateCheckerInfo>
    checkerInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(CheckerInfo.class, HibernateCheckerInfo.class, mapper),
                HibernateCheckerInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<DriverInfo, HibernateDriverInfo> driverInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(DriverInfo.class, HibernateDriverInfo.class, mapper),
                HibernateDriverInfo.class
        );
    }

    @Bean
    public HibernateBaseDao<StringIdKey, HibernateStringIdKey, DriverSupport, HibernateDriverSupport>
    driverSupportHibernateBaseDao() {
        return new HibernateBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, mapper),
                new DozerBeanTransformer<>(DriverSupport.class, HibernateDriverSupport.class, mapper),
                HibernateDriverSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<DriverSupport, HibernateDriverSupport> driverSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(DriverSupport.class, HibernateDriverSupport.class, mapper),
                HibernateDriverSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<DriverSupport, HibernateDriverSupport> driverSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(DriverSupport.class, HibernateDriverSupport.class, mapper),
                HibernateDriverSupport.class,
                driverSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateEntireLookupDao<CheckerInfo, HibernateCheckerInfo> checkerInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(CheckerInfo.class, HibernateCheckerInfo.class, mapper),
                HibernateCheckerInfo.class
        );
    }

    @Bean
    public HibernateBaseDao<StringIdKey, HibernateStringIdKey, CheckerSupport, HibernateCheckerSupport>
    checkerSupportHibernateBaseDao() {
        return new HibernateBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(StringIdKey.class, HibernateStringIdKey.class, mapper),
                new DozerBeanTransformer<>(CheckerSupport.class, HibernateCheckerSupport.class, mapper),
                HibernateCheckerSupport.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<CheckerSupport, HibernateCheckerSupport> checkerSupportHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(CheckerSupport.class, HibernateCheckerSupport.class, mapper),
                HibernateCheckerSupport.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<CheckerSupport, HibernateCheckerSupport> checkerSupportHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(CheckerSupport.class, HibernateCheckerSupport.class, mapper),
                HibernateCheckerSupport.class,
                checkerSupportPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, CheckHistory, HibernateCheckHistory>
    checkHistoryHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(CheckHistory.class, HibernateCheckHistory.class, mapper),
                HibernateCheckHistory.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<CheckHistory, HibernateCheckHistory> checkHistoryHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(CheckHistory.class, HibernateCheckHistory.class, mapper),
                HibernateCheckHistory.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<CheckHistory, HibernateCheckHistory> checkHistoryHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(CheckHistory.class, HibernateCheckHistory.class, mapper),
                HibernateCheckHistory.class,
                checkHistoryPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, AlarmSetting, HibernateAlarmSetting>
    alarmSettingHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(AlarmSetting.class, HibernateAlarmSetting.class, mapper),
                HibernateAlarmSetting.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AlarmSetting, HibernateAlarmSetting> alarmSettingHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmSetting.class, HibernateAlarmSetting.class, mapper),
                HibernateAlarmSetting.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AlarmSetting, HibernateAlarmSetting> alarmSettingHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmSetting.class, HibernateAlarmSetting.class, mapper),
                HibernateAlarmSetting.class,
                alarmSettingPresetCriteriaMaker
        );
    }

    @Bean
    public HibernateBatchBaseDao<LongIdKey, HibernateLongIdKey, AlarmInfo, HibernateAlarmInfo>
    alarmInfoHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(LongIdKey.class, HibernateLongIdKey.class, mapper),
                new DozerBeanTransformer<>(AlarmInfo.class, HibernateAlarmInfo.class, mapper),
                HibernateAlarmInfo.class,
                new DefaultDeletionMod<>(),
                batchSize
        );
    }

    @Bean
    public HibernateEntireLookupDao<AlarmInfo, HibernateAlarmInfo> alarmInfoHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmInfo.class, HibernateAlarmInfo.class, mapper),
                HibernateAlarmInfo.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<AlarmInfo, HibernateAlarmInfo> alarmInfoHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                hibernateTemplate,
                new DozerBeanTransformer<>(AlarmInfo.class, HibernateAlarmInfo.class, mapper),
                HibernateAlarmInfo.class,
                alarmInfoPresetCriteriaMaker
        );
    }
}
