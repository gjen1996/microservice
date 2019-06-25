package com.glen.configbusclient.controller;/**
 * @author Glen
 * @create 2019- 06-2019/6/25-11:19
 * @Description
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Glen
 * @create 2019/6/25 11:19 
 * @Description
 */
public class ConfigBusClientController {
    @RestController
    public class configClientController {
        //@Value("#{remoteSettings['remote.ip']}")
        @Value("${neo.hello}")
        private String hello;

        @RequestMapping("/hello")
        public String from() {
            return this.hello;
        }
    }
}
