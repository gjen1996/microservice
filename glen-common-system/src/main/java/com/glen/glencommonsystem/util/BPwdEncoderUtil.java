package com.glen.glencommonsystem.util;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:06
 * @Description
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Glen
 * @create 2019/6/28 11:06
 * @Description
 */
public class BPwdEncoderUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String BCryptPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
