package com.glen.glensystem.config;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-10:52
 * @Description
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * @author Glen
 * @create 2019/6/28 10:52
 * @Description
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class JwtConfig {
    @Value("${config.oauth2.publicKey}")
    private String publicKey;
    public static final String public_cert = "public.cert";

    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//          converter.setVerifierKey(publicKey);
//        converter.setSigningKey("123");
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource(public_cert);

        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        converter.setVerifierKey(publicKey);
        log.info("public key:" + converter);
        return converter;
    }
}