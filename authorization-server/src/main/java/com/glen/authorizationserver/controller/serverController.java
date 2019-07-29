package com.glen.authorizationserver.controller;/**
 * @author Glen
 * @create 2019- 07-2019/7/24-14:51
 * @Description
 */

import com.glen.authorizationserver.config.UserRepository;
import com.glen.authorizationserver.entity.User;
import com.glen.authorizationserver.service.UserServiceDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Glen
 * @create 2019/7/24 14:51 
 * @Description
 */
@RestController
@EnableResourceServer
@Slf4j
@RequestMapping("/user")
public class serverController {
    @Autowired
    UserServiceDetail userServiceDetail;
    @Autowired
    private UserRepository userRepository;



    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    public User insertUser(String username, String  password){
        User user=new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public UserDetails login(@RequestParam("username") String username) {
        UserDetails re = userRepository.findByUsername(username);
        log.info("login_username---password"+re.getUsername()+"---"+re.getPassword());
        return userRepository.findByUsername(username);
    }

}
