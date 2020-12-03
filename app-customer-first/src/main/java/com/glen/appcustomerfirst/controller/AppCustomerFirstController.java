package com.glen.appcustomerfirst.controller;/**
 * @author Glen
 * @create 2019- 06-2019/6/24-17:02
 * @Description
 */

import com.glen.appcustomerfirst.service.AppCustomerFirstServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Glen
 * @create 2019/6/24 17:02
 * @Description
 */
@RestController
public class AppCustomerFirstController {
    @Autowired
    private AppCustomerFirstServer appCustomerFirstServer;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return appCustomerFirstServer.test();
    }
}
