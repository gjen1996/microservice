//package com.glen.product.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.glen.product.entity.ProductEntity;
//import com.glen.product.methodservice.AppcustomerLoginService;
//import com.glen.product.service.ProductService;
//import com.glen.product.utils.PageUtils;
//import com.glen.product.utils.R;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.Base64Utils;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/product")
//@Slf4j
//public class ProductController {
//	@Autowired
//	ProductService productService;
//	@Autowired
//	AppcustomerLoginService appcustomerLoginService;
//	@Autowired
//	private RedisTemplate redisTemplate;
//	/** 产品列表 */
//	@RequestMapping(value ="/list/{check}", method = RequestMethod.GET)
//	@ResponseBody
//	public R list(@PathVariable("check") String check,@RequestBody JSONObject data) {
//
//		//每次进来做一次产品的上下架修改;
//		productService.changeStatusToAdded();
//		productService.changeStatusToDismounted();
//		JSONObject getToken = appcustomerLoginService.getToken();
//		String username = getToken.getString("username");
//		log.info("getToken--"+getToken);
//		log.info("username:"+username);
//		PageUtils page =productService.queryPage(check,data,username);
//		return R.ok().put("page", page).put("username", username);
//	}
//	/** 筛选器 */
//	@RequestMapping(value ="/select/{check}", method = RequestMethod.GET)
//	@ResponseBody
//	public R select(@PathVariable("check") String check, @RequestParam Map<String, Object> params) {
//		JSONObject getToken = appcustomerLoginService.getToken();
//		String username = getToken.getString("username");
//		log.info("getToken--"+getToken);
//		log.info("username:"+username);
//		PageUtils page = productService.querySelectPage(check,params,username);
//		return R.ok().put("page", page);
//	}
//
//	/**获取单条数据*/
//	@RequestMapping(value ="/info/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public R info(@PathVariable("id") String id) {
//		int returnId =Integer.parseInt(new String(Base64Utils.decodeFromString(id)));
//		ProductEntity productInfo =productService.selectOneProduct(returnId);
//	    return R.ok().put("productInfo", productInfo);
//	   }
//	/** 新增产品 */
//	@RequestMapping(value="/save", method = RequestMethod.POST)
//	@ResponseBody
//	public R saveProduct(@RequestBody ProductEntity productInfo) {
//		JSONObject getToken = appcustomerLoginService.getToken();
//		String username = getToken.getString("username");
//		log.info("getToken--"+getToken);
//		log.info("username:"+username);
//		productInfo.setCreateUser(username);
//		boolean code = productService.saveProduct(productInfo);
//		return R.ok().put("code", code);
//	   }
//	/**修改产品*/
//	@RequestMapping(value="/update", method = RequestMethod.POST)
//	@ResponseBody
//	public R updateProduct(@RequestBody ProductEntity productInfo) {
//		boolean code =productService.updateProduct(productInfo);
//		return R.ok().put("code",code);
//	   }
//	/**删除产品*/
//	@RequestMapping(value ="/delete", method = RequestMethod.POST)
//	@ResponseBody
//	public R delete(@RequestBody Map<String,String> data) {
//		int id = Integer.parseInt(new String(Base64Utils.decodeFromString(data.get("id"))));
//		boolean code = productService.deleteProductById(id);
//		return R.ok().put("code", code);
//	   }
//	/** 下架产品 */
//	@RequestMapping(value="/dismounted",method= RequestMethod.POST)
//	@ResponseBody
//	public R dismountedProduct(@RequestParam Map<String, Object> params) {
//		int id =Integer.parseInt(new String(Base64Utils.decodeFromString((String) params.get("id"))));
//		params.put("id", id);
//		productService.dismountedProduct(params);
//		return R.ok().put("code",true);
//	}
//
//	/** 删除产品审核 */
//	@RequestMapping(value="/productCheckDelete",method= RequestMethod.POST)
//	@ResponseBody
//	public R productCheckDelete(@RequestBody Map<String, String> data) {
//		int id = Integer.parseInt(new String(Base64Utils.decodeFromString(data.get("id"))));
//		boolean code = productService.productCheckDelete(id);
//		return R.ok().put("code",code);
//	}
//	/** 删除发布审核 */
//	@RequestMapping(value="/releaseCheckDelete",method= RequestMethod.POST)
//	@ResponseBody
//	public R releaseCheckDelete(@RequestBody Map<String, String> data) {
//		int id = Integer.parseInt(new String(Base64Utils.decodeFromString(data.get("id"))));
//		boolean code = productService.releaseCheckDelete(id);
//		return R.ok().put("code",code);
//	}
//
//
//	/**提交审核*/
//	@RequestMapping(value="/submitCheck",method= RequestMethod.POST)
//	@ResponseBody
//	public R submitProduct(@RequestBody Map<String, String> data) {
//		int id = Integer.parseInt(new String(Base64Utils.decodeFromString(data.get("id"))));
//		boolean code = productService.submitCheck(id);
//		return R.ok().put("code",code);
//	}
//
//	/** 产品审核 */
//	@RequestMapping(value="/checkProduct",method= RequestMethod.POST)
//	@ResponseBody
//	public R checkProduct(@RequestParam Map<String, Object> params) {
//		int id =Integer.parseInt(new String(Base64Utils.decodeFromString((String) params.get("id"))));
//		params.put("id", id);
//		productService.checkProduct(params);
//		return R.ok().put("code",true);
//	}
//
//	/** 发布 */
//	@RequestMapping(value="/releaseProduct",method= RequestMethod.POST)
//	@ResponseBody
//	public R releaseProduct(@RequestParam Map<String, Object> params) {
//		int id =Integer.parseInt(new String(Base64Utils.decodeFromString((String) params.get("id"))));
//		params.put("id", id);
//		productService.releaseProduct(params);
//		return R.ok().put("code",true);
//	}
//
//	/**购卡列表
//	 * @throws Exception */
//	@RequestMapping(value = "/listSelling", method = RequestMethod.GET)
//	@ResponseBody
//	public R listSelling(@RequestParam Map<String, Object> params) throws Exception {
//
//		 Map<String, Object> dataAuthority = sysUserService.getUserForDataShow(request);
//		  String dataType = (String)dataAuthority.get("showDataType");
//		  boolean checkUser =false;
//		  Page<ProductEntity> page1 = new Page<ProductEntity>();
//	 		PageUtils page = new PageUtils(page1);
//		  switch (dataType) {
//		 	  case "personal":
//		 		 //展示个人数据
//		 		 /**
//		 			 * 加入判断
//		 			 * 1.上架时间，下架时间跟本地时间对比 √
//		 			 * 2.用户归属，等于登陆的账号或者平台    √
//		 			 * 3.判断用户是审核过的用户   √
//		 			 * */
//
//		 			/**
//		 			 * 判断用户是否审核
//		 			 * */
//		 			Map customerMap  = accountCompanyUserInfoService.getCustomerInfo(request);
//		 		    AccountCompanyUserInfoEntity   accountCompanyUserInfoEntity =(AccountCompanyUserInfoEntity)customerMap.get("accountCompanyUserInfoEntity");
//		 		    String status2 ="";
//		 	    	if(accountCompanyUserInfoEntity!=null){
//		 	    		status2=accountCompanyUserInfoEntity.getStatus2();
//		 	    	}
//		 		    HttpSession session = request.getSession();
//		 			String username = (String) session.getAttribute("username");
//		 		    if(status2.equals("1")){
//		 			    	productService.changeStatusToAdded();
//		 					productService.changeStatusToDismounted();
//		 					/*获取到本地账户，判断*/
//		 			    	page = productService.queryPageSelling(params,username,"personal",null);
//		 			    	checkUser=true;
//		 					return R.ok().put("page", page).put("checkUser", checkUser);
//		 		    }else{
//		 		    	checkUser=false;
//		 		    }
//		 		 break;
//		 	  case "total":
//		               //   展示全部数据
//		 		  	checkUser=true;
//		 		  	page = productService.queryPageSelling(params,"","total",null);
//		 		 break;
//		 	  default:
//		 		  // 展示userList这个List用户的数据
//		 		 List<SysUserEntity> userList = (List<SysUserEntity>)dataAuthority.get("userList");
//		 		 checkUser=true;
//		 		 page = productService.queryPageSelling(params,"","default",userList);
//		 		 break;
//		  }
//		  return R.ok().put("page", page).put("checkUser", checkUser);
//	}
//	@ResponseBody
//	@RequestMapping(value = "/getProduct", method = RequestMethod.POST)
//	public ProductEntity returnMap(@RequestParam String productId) {
//		ProductEntity entity = null;
//		try {
//			entity = productService.getProductEntity(productId);
//		} catch (Exception e) {
//		}
//		return entity;
//
//	}
//
//}
