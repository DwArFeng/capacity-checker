package com.dwarfeng.capacitychecker.impl.dao.preset;

import com.dwarfeng.capacitychecker.stack.service.AlarmInfoMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AlarmInfoPresetCriteriaMaker implements PresetCriteriaMaker {

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case AlarmInfoMaintainService.ALARMING:
                childForSectionLevelDesc(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void childForSectionLevelDesc(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            detachedCriteria.add(Restrictions.eq("alarming", true));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
