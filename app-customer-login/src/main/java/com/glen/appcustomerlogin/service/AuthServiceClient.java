package com.glen.appcustomerlogin.service;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:28
 * @Description
 */

import com.glen.appcustomerlogin.entity.JWT;
import com.glen.appcustomerlogin.service.impl.AuthServiceClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Glen
 * @create 2019/6/28 11:28 
 * @Description
 */
@FeignClient(value = "auth-server", fallback = AuthServiceClientFallback.class)
public interface AuthServiceClient {
    @PostMapping("/oauth/token")
    JWT getToken(@RequestHeader("Authorization") String Authorization,
                 @RequestParam("grant_type") String grant_type,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);


}