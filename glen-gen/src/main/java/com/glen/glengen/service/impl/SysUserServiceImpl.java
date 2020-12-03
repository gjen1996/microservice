package com.glen.glengen.service.impl;


import com.glen.glengen.dao.HqlDao;
import com.glen.glengen.dao.impl.HqlDaoImpl;
import com.glen.glengen.entity.SysUserEntity;
import com.glen.glengen.service.ISysUserService;
import org.springframework.stereotype.Service;

public class SysUserServiceImpl implements ISysUserService {

	@Override
	public SysUserEntity findById(String id) {
        HqlDao userDao = new HqlDaoImpl();
		return userDao.findById(SysUserEntity.class,id);
	}
}

