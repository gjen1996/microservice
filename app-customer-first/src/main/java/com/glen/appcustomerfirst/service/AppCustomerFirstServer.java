package com.glen.appcustomerfirst.service;/**
 * @author Glen
 * @create 2019- 06-2019/6/24-17:04
 * @Description
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Glen
 * @create 2019/6/24 17:04 
 * @Description
 */
@FeignClient(value="app-server")
public interface AppCustomerFirstServer {
    @RequestMapping("/test")
    public String test();

}