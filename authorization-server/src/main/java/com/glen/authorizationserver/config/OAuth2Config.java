package com.glen.authorizationserver.config;
/**
 * @author Glen
 * @create 2019- 06-2019/6/28-17:33
 * @Description
 */


import com.glen.authorizationserver.service.UserServiceDetail;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author Glen
 * @create 2019/6/28 17:33
 * @Description
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Value("${config.oauth2.privateKey}")
    private String privateKey ;
    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    @Autowired
    @Qualifier("authenticationManagerBean")
    AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private UserServiceDetail userServiceDetail;
    @Autowired
    private ClientDetailsService clientDetailsService;


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("test-jwt.jks"), "test123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("test-jwt"));
        //converter.setSigningKey("micosrv_signing_key");
//        log.info("jwtAccessTokenConverter privateKey ：" + privateKey);
//        converter.setSigningKey(privateKey);
//        converter.setVerifierKey(publicKey);
//       // converter.setSigningKey("123");
        log.info("private key:"+converter);
        return converter;
    }

    @Bean // 声明 ClientDetails实现
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       String finalSecret =new BCryptPasswordEncoder().encode("123456");
        clients.inMemory()
                // 配置一个客户端
                .withClient("user-service")
                .secret("123456")
                // 配置客户端的域
                .scopes("service")
                // 配置验证类型为refresh_token和password
                .authorizedGrantTypes("refresh_token","password")
                // 配置token的过期时间为1h
                .accessTokenValiditySeconds(3600 * 1000)
                .resourceIds("user-service")
                .authorities("ROLE_ADMIN");

    //  clients.withClientDetails(clientDetailsService);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置token的存储方式为JwtTokenStore
        endpoints.tokenStore(tokenStore())
                // 配置用于JWT私钥加密的增强器
                .tokenEnhancer(jwtTokenEnhancer())
                // 配置安全认证管理
                .authenticationManager(authenticationManager);
//        endpoints
//                //指定认证管理器
//                .authenticationManager(authenticationManager)
//                //用户账号密码认证
//                .userDetailsService(userServiceDetail)
//                // refresh_token
//                .reuseRefreshTokens(false)
//                //指定token存储位置
//                .tokenStore(tokenStore())
//                // 配置JwtAccessToken转换器
//                .accessTokenConverter(accessTokenConverter());
//
//        // 配置tokenServices参数
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(endpoints.getTokenStore());
//        tokenServices.setSupportRefreshToken(false);
//        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
//        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
//        tokenServices.setAccessTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30)); // 30天
//        endpoints.tokenServices(tokenServices);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.tokenKeyAccess("permitAll()") //url:/oauth/token_key,exposes public key for token verification if using JWT tokens
                .checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token
                .allowFormAuthenticationForClients();
    }
}
