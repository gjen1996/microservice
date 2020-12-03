package com.glen.appcustomerlogin.exception;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:35
 * @Description
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Glen
 * @create 2019/6/28 11:35
 * @Description 全局异常处理 切面类 ExceptionHandle
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserLoginException.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.OK);
    }
}
