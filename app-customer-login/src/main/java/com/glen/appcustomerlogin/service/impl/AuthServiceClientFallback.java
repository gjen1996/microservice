package com.glen.appcustomerlogin.service.impl;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:30
 * @Description
 */

import com.glen.appcustomerlogin.entity.JWT;
import com.glen.appcustomerlogin.service.AuthServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * @author Glen
 * @create 2019/6/28 11:30 
 * @Description
 */
@Slf4j
@Component
public class AuthServiceClientFallback implements AuthServiceClient {
    @Override
    public JWT getToken(String Authorization,
                        String grant_type,
                        String username,
                        String password) {
       log.info("您获取令牌失败了哦，请稍后重试");
        return null;
    }
}