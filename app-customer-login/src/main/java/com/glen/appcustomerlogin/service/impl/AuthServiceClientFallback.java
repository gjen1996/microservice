package com.glen.appcustomerlogin.service.impl;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:30
 * @Description
 */

import com.glen.appcustomerlogin.entity.JWTEntity;
import com.glen.appcustomerlogin.service.AuthClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Glen
 * @create 2019/6/28 11:30
 * @Description
 */
@Slf4j
@Component
public class AuthServiceClientFallback implements AuthClientService {
    @Override
    public JWTEntity getToken(String Authorization,
                              String grant_type,
                              String username,
                              String password) {
        log.info("Fallback of getToken is executed");
        return null;
    }

    @Override
    public JWTEntity getNewToken(String client_id,
                                 String client_secret,
                                 String refresh_token,
                                 String grant_type) {
        log.info("Fallback of getToken is executed");
        return null;
    }
}