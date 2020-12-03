package com.glen.appcustomerfirst.service.impl;

import com.glen.appcustomerfirst.service.AppCustomerFirstServer;
import org.springframework.stereotype.Component;

/**
 * @author Glen
 * @create 2019/6/26 10:25
 * @Description
 */
@Component
public class AppCustomerFirstServerFallback implements AppCustomerFirstServer {
    @Override
    public String test() {
        return "程序猿小哥哥，你的服务挂掉了，赶快去修bug。";
    }
}
