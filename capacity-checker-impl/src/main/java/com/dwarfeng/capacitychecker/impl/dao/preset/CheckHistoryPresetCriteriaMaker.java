package com.dwarfeng.capacitychecker.impl.dao.preset;

import com.dwarfeng.capacitychecker.stack.service.CheckHistoryMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
public class CheckHistoryPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case CheckHistoryMaintainService.BETWEEN:
                between(criteria, objs);
                break;
            case CheckHistoryMaintainService.CHILD_FOR_SECTION:
                childForSection(criteria, objs);
                break;
            case CheckHistoryMaintainService.CHILD_FOR_SECTION_BETWEEN:
                childForSectionBetween(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void between(DetachedCriteria criteria, Object[] objs) {
        try {
            Date startDate = (Date) objs[0];
            Date endDate = (Date) objs[1];
            criteria.add(Restrictions.ge("happenedDate", startDate));
            criteria.add(Restrictions.lt("happenedDate", endDate));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForSection(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("sectionLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("sectionLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForSectionBetween(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("sectionLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("sectionLongId", longIdKey.getLongId()));
            }
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];
            criteria.add(Restrictions.ge("happenedDate", startDate));
            criteria.add(Restrictions.lt("happenedDate", endDate));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }
}
