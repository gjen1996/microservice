package com.glen.glensystem.controller;/**
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
import com.glen.glensystem.service.UserLoginService;
import com.glen.glencommonsystem.util.BPwdEncoderUtil;
import com.glen.glensystem.dao.SysUserDao;
import com.glen.glensystem.entity.SysUserEntity;
import com.glen.glensystem.entity.UserLoginDTOEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@RequestMapping("/user")
@RestController
@Slf4j
@Api(tags = {"用户登录"})
public class UserLoginController {
    @Autowired
    UserLoginService userLoginService;
    private SysUserDao sysUserDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${security.oauth2.client.client-id}")
    private String client_id;
    @Value("${security.oauth2.client.client-secret}")
    private String client_secret;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录接口", notes = "参数采用表单提交")
    public UserLoginDTOEntity login(@Valid SysUserEntity loginDto, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        return userLoginService.login(loginDto, bindingResult, response);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(value = "用户注册接口")
    public SysUserEntity postUser(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        SysUserEntity user = new SysUserEntity();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        sysUserDao.insert(user);
        return user;
    }


    @RequestMapping(value = "/foo",method = RequestMethod.POST)
    @ApiOperation(value = "权限测试接口")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getFoo() {
        return "程序猿小哥哥调用成功";
    }


    @RequestMapping(value = "/getToken",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户token")
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

    @RequestMapping(value = "//findByUsername/{username}",method = RequestMethod.GET)
    @ApiOperation(value = "查找用户")
    public SysUserEntity findByUsername(@PathVariable String username) throws Exception {
        return sysUserDao.findByUsername(username);
    }

    @RequestMapping(value = "/getNewToken",method = RequestMethod.POST)
    @ApiOperation(value = "token过期，获取新token")
    public JSONObject getNewToken() {

        String refreshToken = redisTemplate.opsForValue().get("refreshToken").toString();
        log.info("client_id:" + client_id + "--" + "client_secret:" + client_secret + "--" + "refreshToken:" + refreshToken);
        userLoginService.getNewToken(client_id, client_secret, refreshToken, "refresh_token");
        return null;
    }
}