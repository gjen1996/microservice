package com.glen.billing.service;


import com.baomidou.mybatisplus.service.IService;
import com.glen.billing.entity.MonthsPrepayCardEntity;

import java.util.Date;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chenkb3
 * @since 2019-07-09
 */
public interface MonthsPrepayCardService extends IService<MonthsPrepayCardEntity> {
    /**
     * 获取变更时间
     *
     * @param iccid
     * @return
     * @author chenkb3
     */
    Date changePlanTime(String iccid);
}
