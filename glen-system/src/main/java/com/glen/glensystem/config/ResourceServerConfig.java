package com.glen.glensystem.config;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-10:57
 * @Description
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Glen
 * @create 2019/6/28 10:57
 * @Description
 */
@Configuration
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/order/**").authenticated(); // 配置order访问控制，必须认证后才可以访问

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/oauth/token", "/user/login", "/user/register", "/user/getToken",
                        "/user/findByUsername/**", "/user/getNewToken","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/doc.html").permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore).resourceId("user-service");
    }

}
