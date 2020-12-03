package com.glen.appcustomerlogin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glen.appcustomerlogin.entity.SysUserEntity;

/**
 * @author Glen
 * @create 2019/6/28 10:32
 * @Description
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
    SysUserEntity findByUsername(String username);
}

