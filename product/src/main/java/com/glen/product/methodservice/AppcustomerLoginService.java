package com.glen.product.methodservice;/**
 * @author Glen
 * @create 2019- 06-2019/6/24-17:04
 * @Description
 */

import com.alibaba.fastjson.JSONObject;
import com.glen.product.methodservice.fallback.AppcustomerLoginServiceImplFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Glen
 * @create 2019/6/24 17:04
 * @Description
 */
@FeignClient(value = "app-customer-login", fallback = AppcustomerLoginServiceImplFallback.class)
public interface AppcustomerLoginService {

    @RequestMapping("/user/getToken")
    JSONObject getToken();
}