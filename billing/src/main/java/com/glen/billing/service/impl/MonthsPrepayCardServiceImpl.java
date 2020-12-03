package com.glen.billing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.glen.billing.dao.MonthsPrepayCardDao;
import com.glen.billing.entity.MonthsPrepayCardEntity;
import com.glen.billing.service.MonthsPrepayCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenkb3
 * @since 2019-07-09
 */
@Service
@Slf4j
public class MonthsPrepayCardServiceImpl extends ServiceImpl<MonthsPrepayCardDao, MonthsPrepayCardEntity>
        implements MonthsPrepayCardService {

    @Override
    public Date changePlanTime(String iccid) {
        Integer id = getMaxIdByIccid(iccid);
        if (id == null) {
            return null;
        } else {
            return this.selectById(id).getCycleEnd();
        }
    }

    private Integer getMaxIdByIccid(String iccid) {
        EntityWrapper<MonthsPrepayCardEntity> ew = new EntityWrapper<>();
        ew.setSqlSelect("Max(id)");
        ew.eq("iccid", iccid);
        Object obj = this.selectObj(ew);
        return (Integer) obj;
    }

}
