package com.glen.product.methodservice.fallback;


import com.alibaba.fastjson.JSONObject;
import com.glen.product.methodservice.StoreTerminnalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glen
 * @create 2019/6/26 10:25
 * @Description
 */
@Component
@Slf4j
public class StoreTerminnalDetailServiceImplFallback implements StoreTerminnalDetailService {
    @Override
    public Map<String, Object> getStoreTerminalByIccid(String iccid) {
        log.info("getStoreTerminalByIccid方法异常");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "getStoreTerminalByIccid方法异常。");
        return map;
    }

    @Override
    public Integer updateAllColumnById(@RequestBody JSONObject data) {
        log.info("updateAllColumnById方法异常");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "updateAllColumnById方法异常。");
        return 0;
    }

    @Override
    public Integer updateById(@RequestBody JSONObject data) {
        log.info("updateById");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "updateById方法异常。");
        return 0;
    }
}
