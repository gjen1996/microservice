package com.glen.configbusclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Glen
 * @create 2019/6/25 11:19 
 * @Description
 */
@RestController
@RefreshScope
public class ConfigBusClientController {

        @Value("${neo.hello}")
        private String hello;

        @RequestMapping("/hello")
        public String from() {
            return this.hello;
        }

}
