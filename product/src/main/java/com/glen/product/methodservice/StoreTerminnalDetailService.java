package com.glen.product.methodservice;/**
 * @author Glen
 * @create 2019- 06-2019/6/24-17:04
 * @Description
 */

import com.alibaba.fastjson.JSONObject;
import com.glen.product.methodservice.fallback.StoreTerminnalDetailServiceImplFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @author Glen
 * @create 2019/6/24 17:04
 * @Description
 */
@FeignClient(value = "terminnal", fallback = StoreTerminnalDetailServiceImplFallback.class)
public interface StoreTerminnalDetailService {
    /*/**
     * @author Glen
     * @date 2019/8/29 11:02
     * @Description
     */
    @RequestMapping("/terminnal/changeProduct/getStoreTerminalByIccid/{iccid}")
    public Map<String, Object> getStoreTerminalByIccid(@PathVariable String iccid);

    /*/**
     * @author Glen
     * @date 2019/8/29 11:03
     * @Description
     */
    @RequestMapping("/terminnal/changeProduct/updateAllColumnById")
    public Integer updateAllColumnById(@RequestBody JSONObject data);

    @RequestMapping("/terminnal/changeProduct/updateById")
    public Integer updateById(@RequestBody JSONObject data);
}