//package com.glen.appcustomerlogin.service;
//
//import com.baomidou.mybatisplus.service.IService;
//import com.glen.appcustomerlogin.entity.SysUserEntity;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Map;
//
///**
// * 系统用户
// */
//public interface SysUserService extends IService<SysUserEntity> {
//
//	/**数据权限，返回要展示的信息的所属账号信息
//	 * @return
//	 * @throws Exception
//	 * @用法
//	  Map<String, Object> dataAuthority = sysUserService.getUserForDataShow(request);
//	  String dataType = (String)dataAuthority.get("showDataType");
//	  switch (dataType) {
//	 	  case "personal":
//	 		 展示个人数据
//	 		 break;
//	 	  case "total":
//	                  展示全部数据
//	 		 break;
//	 	  default:
//	 		 List<SysUserEntity> userList = (List<SysUserEntity>)dataAuthority.get("userList");
//	                  展示userList这个List用户的数据
//	 		 break;
//	  }
//	 */
//	public Map<String, Object> getUserForDataShow(HttpServletRequest request) throws Exception;
//}
