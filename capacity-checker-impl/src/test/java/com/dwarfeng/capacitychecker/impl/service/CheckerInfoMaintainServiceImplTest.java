package com.dwarfeng.capacitychecker.impl.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckerInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.service.CheckerInfoMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CheckerInfoMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private CheckerInfoMaintainService checkerInfoMaintainService;

    private Section parentSection;
    private CheckerInfo checkerInfo;

    @Before
    public void setUp() {
        parentSection = new Section(
                null, "parent-section", true, 12450L, "test-section"
        );
        checkerInfo = new CheckerInfo(
                parentSection.getKey(), true, "checker-info", "this is a test", "test"
        );
    }

    @After
    public void tearDown() {
        parentSection = null;
        checkerInfo = null;
    }

    @Test
    public void test() throws Exception {
        try {
            parentSection.setKey(sectionMaintainService.insertOrUpdate(parentSection));
            checkerInfo.setKey(parentSection.getKey());
            checkerInfo.setKey(checkerInfoMaintainService.insertOrUpdate(checkerInfo));
            checkerInfoMaintainService.update(checkerInfo);
            CheckerInfo testCheckerInfo = checkerInfoMaintainService.get(checkerInfo.getKey());
            assertEquals(BeanUtils.describe(checkerInfo), BeanUtils.describe(testCheckerInfo));
        } finally {
            checkerInfoMaintainService.deleteIfExists(checkerInfo.getKey());
            sectionMaintainService.deleteIfExists(parentSection.getKey());
        }
    }
}
