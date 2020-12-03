package com.glen.glengen.service;

import com.glen.glengen.entity.SysUserEntity;

import javax.servlet.http.HttpSession;
import java.util.List;


public interface ISysUserService {

    SysUserEntity findById(String id);

}
