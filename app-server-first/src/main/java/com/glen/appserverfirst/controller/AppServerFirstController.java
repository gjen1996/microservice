package com.glen.appserverfirst.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Glen
 * @create 2019- 06-2019/6/24-16:32
 * @Description
 */
@RestController
public class AppServerFirstController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/test")
    public String test(){
        return "Hello,world，恭喜您调用成功了，这个是appServerFirst,port为：" +port;
    }
}
