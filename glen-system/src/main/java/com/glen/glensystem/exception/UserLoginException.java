package com.glen.glensystem.exception;

/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:34
 * @Description 登录异常类 UserLoginException
 */

public class UserLoginException extends RuntimeException {
    public UserLoginException(String message) {
        super(message);
    }
}
