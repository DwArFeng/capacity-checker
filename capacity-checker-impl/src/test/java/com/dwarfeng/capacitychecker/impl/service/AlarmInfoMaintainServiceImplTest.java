package com.dwarfeng.capacitychecker.impl.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.AlarmInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.service.AlarmInfoMaintainService;
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

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class AlarmInfoMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private AlarmInfoMaintainService alarmInfoMaintainService;

    private Section parentSection;
    private AlarmInfo alarmInfo;

    @Before
    public void setUp() {
        parentSection = new Section(new LongIdKey(Long.MAX_VALUE), "name", true, 10240, "remark", 12450);
        alarmInfo = new AlarmInfo(
                new LongIdKey(Long.MAX_VALUE), 10240, 1024, 0.1, new Date(), "alarm_message", true, 12450
        );
    }

    @After
    public void tearDown() {
        parentSection = null;
        alarmInfo = null;
    }

    @Test
    public void testForCurd() throws Exception {
        try {
            sectionMaintainService.insertOrUpdate(parentSection);
            alarmInfoMaintainService.insert(alarmInfo);
            AlarmInfo testAlarmInfo = alarmInfoMaintainService.get(this.alarmInfo.getKey());
            assertEquals(BeanUtils.describe(alarmInfo), BeanUtils.describe(testAlarmInfo));
        } finally {
            sectionMaintainService.deleteIfExists(parentSection.getKey());
            alarmInfoMaintainService.deleteIfExists(alarmInfo.getKey());
        }
    }

    @Test
    public void testForSectionCascade() throws Exception {
        try {
            sectionMaintainService.insertOrUpdate(parentSection);
            alarmInfoMaintainService.insert(alarmInfo);

            sectionMaintainService.deleteIfExists(parentSection.getKey());
            assertFalse(alarmInfoMaintainService.exists(alarmInfo.getKey()));
        } finally {
            sectionMaintainService.deleteIfExists(parentSection.getKey());
            alarmInfoMaintainService.deleteIfExists(alarmInfo.getKey());
        }
    }
}
