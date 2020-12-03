package com.glen.product.methodservice.fallback;

import com.alibaba.fastjson.JSONObject;
import com.glen.product.methodservice.AppcustomerLoginService;
import com.glen.product.methodservice.MonthsPrepayCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Glen
 * @create 2019/6/26 10:25
 * @Description
 */
@Component
@Slf4j
public class AppcustomerLoginServiceImplFallback implements AppcustomerLoginService {
    /**
     * @author Glen
     * @date 2019/8/29 16:13
     * @Description
     */
    @Override
    public JSONObject getToken() {
        log.info("getToken方法异常");
        return null;
    }
}
