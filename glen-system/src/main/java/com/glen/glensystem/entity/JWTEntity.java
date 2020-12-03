package com.glen.glensystem.entity;

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
