package com.glen.product.methodservice.fallback;

import com.alibaba.fastjson.JSONObject;
import com.glen.product.methodservice.MonthsPrepayCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

/**
 * @author Glen
 * @create 2019/6/26 10:25
 * @Description
 */
@Component
@Slf4j
public class MonthsPrepayCardServiceImplFallback implements MonthsPrepayCardService {
    /**
     * @author Glen
     * @date 2019/8/29 16:13
     * @Description
     */
    @Override
    public String changePlanTime(@RequestBody JSONObject data) {
        log.info("changePlanTime方法异常");
        return null;
    }
}
