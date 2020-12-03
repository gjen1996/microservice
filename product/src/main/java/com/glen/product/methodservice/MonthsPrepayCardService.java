package com.glen.product.methodservice;/**
 * @author Glen
 * @create 2019- 06-2019/6/24-17:04
 * @Description
 */

import com.alibaba.fastjson.JSONObject;
import com.glen.product.methodservice.fallback.MonthsPrepayCardServiceImplFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author Glen
 * @create 2019/6/24 17:04
 * @Description
 */
@FeignClient(value = "billing", fallback = MonthsPrepayCardServiceImplFallback.class)
public interface MonthsPrepayCardService {
    /**
     * 获取变更时间
     *
     * @param data
     * @return data 里面是iccid
     * @author chenkb3
     */
    @RequestMapping("/billing/changePlanTime")
    String changePlanTime(@RequestBody JSONObject data);
}