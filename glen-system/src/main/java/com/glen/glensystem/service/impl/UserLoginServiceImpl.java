package com.glen.glensystem.service.impl;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:05
 * @Description
 */

import com.auth0.jwt.JWT;

import com.glen.glencommonsystem.util.BPwdEncoderUtil;
import com.glen.glensystem.dao.SysUserDao;
import com.glen.glensystem.entity.JWTEntity;
import com.glen.glensystem.entity.SysUserEntity;
import com.glen.glensystem.entity.UserLoginDTOEntity;
import com.glen.glensystem.service.AuthClientService;
import com.glen.glensystem.service.UserLoginService;
import com.glen.glensystem.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Base64;
import java.util.Collections;


/**
 * @author Glen
 * @create 2019/6/28 11:05
 * @Description
 */
@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;
    @Autowired
    UserLoginServiceImpl userServiceDetail;
    @Value("${security.oauth2.client.grant-type}")
    String grantType;
    @Value("${security.oauth2.client.scope}")
    String scope;
    @Value("${security.oauth2.client.access-token-uri}")
    String accessTokenUri;
    @Value("${security.oauth2.client.client-id}")
    String clientId;
    @Value("${security.oauth2.client.client-secret}")
    String secret;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthClientService client;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //登录获取access_token
    @Override
    public UserLoginDTOEntity login(@Valid SysUserEntity loginDto, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception("登录信息错误，请确认后再试");
        }
        log.info(loginDto.getUsername() + "---" + loginDto.getPassword());
        SysUserEntity user = sysUserDao.findByUsername(loginDto.getUsername());
        log.info("user:" + user);
        if (null == user) {
            throw new Exception("用户为空，出错了");
        }

        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), user.getPassword().replace("{bcrypt}", ""))) {
            throw new Exception("密码不正确");
        }

        String client_secret = oAuth2ClientProperties.getClientId() + ":" + oAuth2ClientProperties.getClientSecret();

        client_secret = "Basic " + Base64.getEncoder().encodeToString(client_secret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", client_secret);

        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(loginDto.getUsername()));
        map.put("password", Collections.singletonList(loginDto.getPassword()));
        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
        map.put("scope", oAuth2ProtectedResourceDetails.getScope());
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(map, httpHeaders);
        //获取 Token

        // log.info("client_secret:"+client_secret+"loginDto.getUsername:"+loginDto.getUsername()+"--loginDto.getUsername:"+loginDto.getPassword()+"123:"+oAuth2ProtectedResourceDetails.getAccessTokenUri()+"httpEntity:"+httpEntity+"OAuth2AccessToken.class:"+OAuth2AccessToken.class);
        //第一种方式获取jwt
        // 从auth-service获取JWT
        JWTEntity jwt = client.getToken(client_secret, "password", loginDto.getUsername(), loginDto.getPassword());
        if (jwt == null) {
            jwt = client.getToken(client_secret, "password", loginDto.getUsername(), loginDto.getPassword());
        }
        // log.info("jwt.getAccess_token()"+jwt.getAccess_token());
        log.info("jwt----" + jwt);
        redisTemplate.opsForValue().set("username", loginDto.getUsername());
        redisTemplate.opsForValue().set("token", jwt.getAccess_token());
        redisTemplate.opsForValue().set("userId", user.getId());
        redisTemplate.opsForValue().set("refreshToken", jwt.getRefresh_token());
        CookieUtils.writeCookie(response, "token", jwt.getAccess_token());
        CookieUtils.writeCookie(response, "userinfo", loginDto.getUsername());
        UserLoginDTOEntity userLoginDTOEntity = new UserLoginDTOEntity();
        userLoginDTOEntity.setJwt(jwt);
        userLoginDTOEntity.setUser(loginDto);
        return userLoginDTOEntity;
//        //第二种方式获取
//        ResponseEntity<OAuth2AccessToken> re =restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
//        if (re.getStatusCode() != HttpStatus.OK) {
//            log.debug("failed to authenticate user with OAuth2 token endpoint, status: {}",
//                    re.getStatusCodeValue());
//            throw new HttpClientErrorException(re.getStatusCode());
//        }
//        OAuth2AccessToken oAuth2AccessToken = re.getBody();
//        log.info("re----"+re);
//        log.info("re12----"+restTemplate.exchange(accessTokenUri, HttpMethod.POST,httpEntity,OAuth2AccessToken.class));
//        return restTemplate.exchange(accessTokenUri, HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
    }

    @Override
    public JWTEntity getNewToken(String client_id, String client_secret, String refresh_token, String grant_type) {
        //第一种方式获取jwt
        // 从auth-service获取JWT
        JWTEntity jwt = client.getToken(client_id, client_secret, refresh_token, grant_type);
        if (jwt == null) {
            jwt = client.getToken(client_id, client_secret, refresh_token, grant_type);
        }
        // log.info("jwt.getAccess_token()"+jwt.getAccess_token());
        log.info("jwt----" + jwt);
//        redisTemplate.opsForValue().set("username",loginDto.getUsername());
//        redisTemplate.opsForValue().set("token",jwt.getAccess_token());
//        redisTemplate.opsForValue().set("userId",user.getId());
//        CookieUtils.writeCookie(response, "token", jwt.getAccess_token());
//        CookieUtils.writeCookie(response, "userinfo", loginDto.getUsername());
//    UserLoginDTOEntity userLoginDTOEntity =new UserLoginDTOEntity();
//        userLoginDTOEntity.setJwt(jwt);
//        userLoginDTOEntity.setUser(loginDto);
//        return userLoginDTOEntity;
//        //第二种方式获取
//        ResponseEntity<OAuth2AccessToken> re =restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
//        if (re.getStatusCode() != HttpStatus.OK) {
//            log.debug("failed to authenticate user with OAuth2 token endpoint, status: {}",
//                    re.getStatusCodeValue());
//            throw new HttpClientErrorException(re.getStatusCode());
//        }
//        OAuth2AccessToken oAuth2AccessToken = re.getBody();
//        log.info("re----"+re);
//        log.info("re12----"+restTemplate.exchange(accessTokenUri, HttpMethod.POST,httpEntity,OAuth2AccessToken.class));
//        return restTemplate.exchange(accessTokenUri, HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
        return jwt;
    }
}
