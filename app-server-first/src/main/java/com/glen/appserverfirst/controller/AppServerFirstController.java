package com.glen.appserverfirst.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;

/**
 * @author Glen
 * @create 2019- 06-2019/6/24-16:32
 * @Description
 */
@RestController
@EnableResourceServer
public class AppServerFirstController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/test")
    public String test() {
        return "Hello,world，恭喜您调用成功了，这个是appServerFirst,port为：" + port;
    }

    @RequestMapping("/totest")
    public String test1() {
        return "Hello,world，恭喜您调用成功了，这个测试二是appServerFirst,port为：" + port;
    }
}
