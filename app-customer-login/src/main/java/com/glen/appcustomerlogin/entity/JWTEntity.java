package com.glen.appcustomerlogin.entity;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:32
 * @Description
 * @author Glen
 * @create 2019/6/28 11:32
 * @Description
 */

/**
 * @author Glen
 * @create 2019/6/28 11:32 
 * @Description
 */

import lombok.Data;

@Data
public class JWTEntity {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;
}
