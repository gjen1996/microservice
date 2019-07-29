package com.glen.appcustomerlogin.entity;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:33
 * @Description
 */

import lombok.Data;

/**
 * @author Glen
 * @create 2019/6/28 11:33 
 * @Description
 */
@Data
public class UserLoginDTO {
    private JWT jwt;
    private User user;
}
