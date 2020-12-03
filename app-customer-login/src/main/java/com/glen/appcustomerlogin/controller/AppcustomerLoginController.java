package com.glen.appcustomerlogin.controller;/**
 * @author Glen
 * @create 2019- 07-2019/7/9-17:21
 * @Description
 * @author Glen
 * @create 2019/7/9 17:21
 * @Description
 */

/**
 * @author Glen
 * @create 2019/7/9 17:21
 * @Description
 */

import com.alibaba.fastjson.JSONObject;
import com.glen.appcustomerlogin.config.BPwdEncoderUtil;
import com.glen.appcustomerlogin.dao.SysUserDao;
import com.glen.appcustomerlogin.entity.SysUserEntity;
import com.glen.appcustomerlogin.entity.UserLoginDTOEntity;
import com.glen.appcustomerlogin.service.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
@Slf4j
public class AppcustomerLoginController {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${security.oauth2.client.client-id}")
    private String client_id;
    @Value("${security.oauth2.client.client-secret}")
    private String client_secret;

    @RequestMapping("/login")
    public UserLoginDTOEntity login(@Valid SysUserEntity loginDto, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        return userLoginService.login(loginDto, bindingResult, response);
    }

    @PostMapping("/register")
    public SysUserEntity postUser(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        SysUserEntity user = new SysUserEntity();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        sysUserDao.insert(user);
        return user;
    }

    @RequestMapping("/foo")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getFoo() {
        return "程序猿小哥哥调用成功";
    }

    @RequestMapping("/getToken")
    public JSONObject getToken(HttpServletRequest request) {
        JSONObject data = new JSONObject();
        String username = redisTemplate.opsForValue().get("username").toString();
        String token = redisTemplate.opsForValue().get("token").toString();
        String userId = redisTemplate.opsForValue().get("userId").toString();
        log.info("redis:" + username + token);
        data.put("username", username);
        data.put("token", token);
        data.put("userId", userId);
        log.info("data:" + data);
        return data;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0) {
//            Cookie[] var = cookies;
//            int var2 = cookies.length;
//            log.info("var2:"+var2);
//            for (int var3 = 0; var3 < var2; ++var3) {
//                Cookie cookie = var[var3];
//                log.info("test:"+cookie.getName()+"--"+cookie.getValue());
//                if (cookie.getName().equals("token")) {
//                    token = cookie.getValue();
//                }
//                if (cookie.getName().equals("userinfo")) {
//                    username = cookie.getValue();
//                }
//            }
//        }
//        log.info("12345:"+username+token);
    }

    @RequestMapping("/findByUsername/{username}")
    public SysUserEntity findByUsername(@PathVariable String username) throws Exception {
        return sysUserDao.findByUsername(username);
    }

    @RequestMapping("/getNewToken")
    public JSONObject getNewToken() {

        String refreshToken = redisTemplate.opsForValue().get("refreshToken").toString();
        log.info("client_id:" + client_id + "--" + "client_secret:" + client_secret + "--" + "refreshToken:" + refreshToken);
        userLoginService.getNewToken(client_id, client_secret, refreshToken, "refresh_token");
        return null;
    }
}