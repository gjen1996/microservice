package com.glen.billing.controller;

import com.alibaba.fastjson.JSONObject;
import com.glen.billing.service.MonthsPrepayCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Map;


@RequestMapping("/billing")
@RestController
@Slf4j
public class MonthsPrepayCardController {

    @Autowired
    MonthsPrepayCardService monthsPrepayCardService;

    /**
     * 获取登陆用户所有产品
     */

    @RequestMapping("/changePlanTime")
    public String changePlanTime(@RequestBody JSONObject data) {
        String iccid = data.getString("iccid");
        String result;
        if (monthsPrepayCardService.changePlanTime(iccid) == null) {
            result = "error";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            java.util.Date date = new java.util.Date();
            result = sdf.format(monthsPrepayCardService.changePlanTime(iccid));
        }
        monthsPrepayCardService.changePlanTime(iccid);
        return result;
    }

}