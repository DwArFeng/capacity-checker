package com.dwarfeng.capacitychecker.impl.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmSetting;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.service.AlarmSettingMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AlarmSettingMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private AlarmSettingMaintainService alarmSettingMaintainService;

    private Section parentSection;
    private List<AlarmSetting> alarmSettings;

    @Before
    public void setUp() {
        parentSection = new Section(
                new LongIdKey(Long.MAX_VALUE), "name", true, 12450L, "remark"
        );
        alarmSettings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AlarmSetting alarmSetting = new AlarmSetting(
                    null, parentSection.getKey(), 0.1 * i, "alarm_message", "测试用报警设置"
            );
            alarmSettings.add(alarmSetting);
        }
    }

    @After
    public void tearDown() {
        parentSection = null;
        alarmSettings.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            sectionMaintainService.insertOrUpdate(parentSection);
            for (AlarmSetting alarmSetting : alarmSettings) {
                alarmSetting.setKey(alarmSettingMaintainService.insert(alarmSetting));
                alarmSettingMaintainService.update(alarmSetting);
                AlarmSetting testAlarmSetting = alarmSettingMaintainService.get(alarmSetting.getKey());
                assertEquals(BeanUtils.describe(alarmSetting), BeanUtils.describe(testAlarmSetting));
            }
        } finally {
            sectionMaintainService.deleteIfExists(parentSection.getKey());
            for (AlarmSetting alarmSetting : alarmSettings) {
                alarmSettingMaintainService.deleteIfExists(alarmSetting.getKey());
            }
        }
    }
}
