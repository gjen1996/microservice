//package com.glen.appcustomerlogin.service.impl;
//
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.baomidou.mybatisplus.service.impl.ServiceImpl;
//import com.glen.appcustomerlogin.dao.SysUserDao;
//import com.glen.appcustomerlogin.entity.SysUserEntity;
//import com.glen.appcustomerlogin.service.SysUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletRequest;
//import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 系统用户
// */
////@Slf4j
////@Service("sysUserService")
////public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
////	@Override
////	public Map<String, Object> getUserForDataShow(HttpServletRequest request) throws Exception {
////		Map<String, Object> returnResult = new HashMap<>();
////		String userId = request.getSession().getAttribute("userId").toString();
////
////		EntityWrapper<SysUserEntity> ewUserRole = new EntityWrapper<>();
////		ewUserRole.eq("user_id", userId);
////		SysUserEntity sysUserRoleEntity = sysUserRoleService.selectOne(ewUserRole);
////		if (sysUserRoleEntity != null && String.valueOf(sysUserRoleEntity.getRoleId()).equals("4")) {
////			List<SysUserEntity> listUser = sysUserDao.getManagerUserEntity(userId);
////			returnResult.put("showDataType", "someUser");
////			returnResult.put("userList", listUser);
////			return returnResult;
////		}
////
////		List<SysRoleDeptEntity> roleDeptList = sysUserDao.getRoleDeptEntity(userId);
////
////        if (userId.equals("1")) {
////        	returnResult.put("showDataType", "total");
////		}else if (roleDeptList == null || roleDeptList.size() == 0) {
////			returnResult.put("showDataType", "personal");
////		}else if (roleDeptList.size() >= sysDeptService.selectCount(null)) {
////			returnResult.put("showDataType", "total");
////		}else {
////			List<SysUserEntity> userList = new ArrayList<>();
////			EntityWrapper<SysUserEntity> ewUser = new EntityWrapper<>();
////
////			ewUser.eq("dept_id", String.valueOf(roleDeptList.get(0).getDeptId()));
////			for (int i = 1, j = roleDeptList.size(); i < j; i++) {
////				ewUser.or().eq("dept_id", String.valueOf(roleDeptList.get(i).getDeptId()));
////			}
////
////			userList = sysUserDao.selectList(ewUser);
////			ewUser = new EntityWrapper<>();
////			ewUser.eq("user_id", userId);
////			SysUserEntity sysUserEntity = this.selectOne(ewUser);
////			if (!userList.contains(sysUserEntity)) {
////				userList.add(sysUserEntity);
////			}
////			returnResult.put("showDataType", "someUser");
////			returnResult.put("userList", userList);
////		}
////		return returnResult;
////	}
////}
