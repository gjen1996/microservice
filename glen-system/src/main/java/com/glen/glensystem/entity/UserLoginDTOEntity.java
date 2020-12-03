package com.glen.glensystem.entity;/**
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
public class UserLoginDTOEntity {
    private JWTEntity jwt;
    private SysUserEntity user;
}
