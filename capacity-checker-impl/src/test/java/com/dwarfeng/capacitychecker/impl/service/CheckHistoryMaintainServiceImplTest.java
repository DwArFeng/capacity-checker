package com.dwarfeng.capacitychecker.impl.service;

import com.dwarfeng.capacitychecker.stack.bean.entity.CheckHistory;
import com.dwarfeng.capacitychecker.stack.bean.entity.Section;
import com.dwarfeng.capacitychecker.stack.service.CheckHistoryMaintainService;
import com.dwarfeng.capacitychecker.stack.service.SectionMaintainService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CheckHistoryMaintainServiceImplTest {

    @Autowired
    private SectionMaintainService sectionMaintainService;
    @Autowired
    private CheckHistoryMaintainService checkHistoryMaintainService;

    private Section parentSection;
    private List<CheckHistory> statisticsHistories;

    @Before
    public void setUp() {
        parentSection = new Section(null, "parent-section", true, 12450L, "test-section", 12450);
        statisticsHistories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CheckHistory checkHistory = new CheckHistory(
                    null, parentSection.getKey(), 12450L, 12450L, 1.0, new Date(), 12450
            );
            statisticsHistories.add(checkHistory);
        }
    }

    @After
    public void tearDown() {
        parentSection = null;
        statisticsHistories.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentSection.setKey(sectionMaintainService.insert(parentSection));
            for (CheckHistory checkHistory : statisticsHistories) {
                checkHistory.setKey(checkHistoryMaintainService.insert(checkHistory));
                checkHistory.setSectionKey(parentSection.getKey());
                checkHistoryMaintainService.update(checkHistory);
            }
        } finally {
            for (CheckHistory checkHistory : statisticsHistories) {
                checkHistoryMaintainService.delete(checkHistory.getKey());
            }
            sectionMaintainService.delete(parentSection.getKey());
        }
    }
}
