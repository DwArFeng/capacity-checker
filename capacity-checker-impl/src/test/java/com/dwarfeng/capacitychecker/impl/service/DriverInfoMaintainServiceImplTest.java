package com.dwarfeng.capacitychecker.impl.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.DriverInfo;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.service.DriverInfoMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
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
public class DriverInfoMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private DriverInfoMaintainService driverInfoMaintainService;

    private Section parentSection;
    private List<DriverInfo> driverInfos;

    @Before
    public void setUp() {
        parentSection = new Section(
                null, "parent-section", true, 12450L, "test-section"
        );
        driverInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DriverInfo driverInfo = new DriverInfo(
                    null, parentSection.getKey(), true, "driver-info-" + i, "this is a test", "test"
            );
            driverInfos.add(driverInfo);
        }
    }

    @After
    public void tearDown() {
        parentSection = null;
        driverInfos.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            parentSection.setKey(sectionMaintainService.insertOrUpdate(parentSection));
            for (DriverInfo driverInfo : driverInfos) {
                driverInfo.setKey(driverInfoMaintainService.insertOrUpdate(driverInfo));
                driverInfo.setSectionKey(parentSection.getKey());
                driverInfoMaintainService.update(driverInfo);
                DriverInfo testDriverInfo = driverInfoMaintainService.get(driverInfo.getKey());
                assertEquals(BeanUtils.describe(driverInfo), BeanUtils.describe(testDriverInfo));
            }
        } finally {
            for (DriverInfo driverInfo : driverInfos) {
                driverInfoMaintainService.deleteIfExists(driverInfo.getKey());
            }
            sectionMaintainService.deleteIfExists(parentSection.getKey());
        }
    }
}
