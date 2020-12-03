package com.glen.appcustomerlogin.service;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:28
 * @Description
 */

import com.glen.appcustomerlogin.entity.JWTEntity;
import com.glen.appcustomerlogin.service.impl.AuthServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Glen
 * @create 2019/6/28 11:28
 * @Description
 */
@FeignClient(value = "auth-server", fallback = AuthServiceClientFallback.class)
public interface AuthClientService {
    @PostMapping("/oauth/token")
    JWTEntity getToken(@RequestHeader("Authorization") String Authorization,
                       @RequestParam("grant_type") String grant_type,
                       @RequestParam("username") String username,
                       @RequestParam("password") String password);

    @PostMapping("/oauth/token")
    JWTEntity getNewToken(@RequestHeader("refresh_token") String refresh_token,
                          @RequestParam("grant_type") String grant_type,
                          @RequestParam("client_id") String client_id,
                          @RequestParam("client_secret") String client_secret);
}