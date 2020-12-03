package com.glen.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glen.product.dao.ProductDao;
import com.glen.product.entity.ProductEntity;
import com.glen.product.methodservice.StoreTerminnalDetailService;
import com.glen.product.service.ProductService;
import com.glen.product.utils.PageUtils;
import com.glen.product.utils.Query;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Autowired
    HttpServletRequest request;
    //@Autowired
    //EditTerminalService editTerminalService;
    @Autowired
    StoreTerminnalDetailService storeTerminnalDetailService;

    /**
     * 查询框
     */
    @Override
    public PageUtils queryPage(String check, JSONObject data, String username) {
        String key = data.getString("key");
        Page<ProductEntity> page = null;
        EntityWrapper<ProductEntity> ew = new EntityWrapper<ProductEntity>();
        if (key == null || "".equals(key)) {
            if ("productCheck".equals(check)) {
                ew.andNew().where("1=1", 1).eq("product_check", 1);
            } else if ("releaseCheck".equals(check)) {
                ew.andNew().where("1=1", 1).eq("release_check", 1);
            } else {
                ew.andNew().where("1=1 and is_delete = 0 and(status = 'added' or create_user ='" + username + "')");
            }
            page = this.selectPage(new Query<ProductEntity>(data).getPage(), ew);
            return new PageUtils(page);
        } else {
            if ("productCheck".equals(check)) {
                ew.andNew().where("1=1 and product_check=1 ").andNew().like("product_id", key).or().like("product_name", key).or().like("owner", key);
            } else if ("releaseCheck".equals(check)) {
                ew.andNew().where("1=1  and release_check=1").andNew().like("product_id", key).or().like("product_name", key).or().like("owner", key);
            } else {
                ew.andNew().where("1=1 and is_delete = 0 and (create_user='" + username + "' or status='added' )").andNew().like("product_id", key).or().like("product_name", key).or().like("owner", key);
            }
            page = this.selectPage(new Query<ProductEntity>(data).getPage(), ew);
            return new PageUtils(page);
        }

    }

    @Override
    public void changeStatusToAdded() {
        baseMapper.changeStatusToAdded();
    }

    @Override
    public void changeStatusToDismounted() {
        baseMapper.changeStatusToDismounted();
    }

    /**
     * 筛选器
     */
    @Override
    public PageUtils querySelectPage(String check, Map<String, Object> params, String username) {
        String productId = (String) params.get("productId");
        String productName = (String) params.get("productName");
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        String owner = (String) params.get("owner");
        String status = (String) params.get("status");
        Page<ProductEntity> page = null;
        EntityWrapper<ProductEntity> ew = new EntityWrapper<ProductEntity>();
        if ("".equals(productId) && "".equals(productName) && "".equals(startDate) && "".equals(endDate) && "".equals(owner) && ("".equals(status))) {
            if ("productCheck".equals(check)) {
                ew.andNew().where("1=1", 1).eq("product_check", 1);
            } else if ("releaseCheck".equals(check)) {
                ew.andNew().where("1=1", 1).eq("release_check", 1);
            } else {
                ew.andNew().where("1=1 and is_delete = 0").eq("create_user", username).or().eq("status", "added");
            }
            page = this.selectPage(new Query<ProductEntity>(params).getPage(), ew);
            return new PageUtils(page);
        } else {
            if ("productCheck".equals(check)) {
                ew.andNew().where("1 =1 ").eq("product_check", 1).like("product_id", productId).like("product_name", productName).like("owner", owner);
            } else if ("releaseCheck".equals(check)) {
                ew.andNew().where("1 =1 ").eq("release_check", 1).like("product_id", productId).like("product_name", productName).like("owner", owner);
            } else {
                ew.andNew().where("is_delete = 0 and  (create_user='" + username + "' or status='added' )").like("product_id", productId).like("product_name", productName).like("owner", owner).like("status", status);
                if (!"".equals(status)) {
                    ew.eq("status", status);
                }
            }
            if (!"".equals(startDate)) {
                ew.ge("added_date", startDate);
            }
            if (!"".equals(endDate)) {
                ew.le("dismounted_date", endDate);
            }
            page = this.selectPage(new Query<ProductEntity>(params).getPage(), ew);
            return new PageUtils(page);
        }
    }

    /**
     * 查询单个产品
     */
    @Override
    public ProductEntity selectOneProduct(int id) {

        ProductEntity productEntity = this.selectById(id);
        return productEntity;
    }

    /**
     * 删除产品
     */
    @Override
    public boolean deleteProductById(int id) {
        boolean code = baseMapper.deleteProductById(id);
        return code;
    }

    /**
     * 新增产品
     */
    @Override
    public boolean saveProduct(ProductEntity productEntity) {
        Date createDate = new Date(System.currentTimeMillis());
        productEntity.setCreateDate(createDate);
        String today = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String productId = "CP" + today;
        int data = Integer.parseInt(productEntity.getData().toString());
        String Unit = "M";
        if ((data >= 1024)) {
            data = data / 1024;
            Unit = "G";
        }
        String ratePlanName = productEntity.getOwner() + "_" + productEntity.getSharingMode() + "_" + data + Unit;
        productEntity.setRatePlanName(ratePlanName);
        productEntity.setProductId(productId);
        boolean code = this.insert(productEntity);
        return code;
    }

    /**
     * 修改产品
     */
    @Override
    public boolean updateProduct(ProductEntity productEntity) {
        productEntity.setStatus("design");
        productEntity.setProductCheck(0);
        productEntity.setReleaseCheck(0);
        int data = Integer.parseInt(productEntity.getData().toString());
        String Unit = "M";
        if ((data >= 1024)) {
            data = data / 1024;
            Unit = "G";
        }

        String ratePlanName = productEntity.getOwner() + "_" + productEntity.getSharingMode() + "_" + data + Unit;
        productEntity.setRatePlanName(ratePlanName);
        boolean code = this.updateById(productEntity);
        return code;
    }


    /**
     * 发布产品
     */
    @Override
    public void releaseProduct(Map<String, Object> params) {
        baseMapper.releaseProduct(params);
    }

    /**
     * 购卡列表
     */
//	@Override
//	public PageUtils queryPageSelling(Map<String, Object> params,String username,String roleType,List<SysUserEntity> userList) {
//		String todayDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		Page<ProductEntity> page = null;
//		EntityWrapper<ProductEntity> ew = new EntityWrapper<ProductEntity>();
//		String key = (String) params.get("key");
//		if(roleType.equals("personal")){
//			if(key==null|| "".equals(key)){
//					ew.andNew().where("1=1 and (owner = '"+username+"' or owner = '平台') ").and().le("added_date", todayDate).ge("dismounted_date", todayDate).eq("status", "added");
//			}else{
//					ew.andNew().where("1=1 and (owner = '"+username+"' or owner = '平台') ").le("added_date", todayDate).ge("dismounted_date", todayDate).eq("status", "added").andNew().like("product_name", key);
//			}
//		}else if(roleType.equals("total")){
//			if(key==null|| "".equals(key)){
//				ew.andNew().where("1=1").and().le("added_date", todayDate).ge("dismounted_date", todayDate).eq("status", "added");
//			}else{
//					ew.andNew().where("1=1").le("added_date", todayDate).ge("dismounted_date", todayDate).eq("status", "added").andNew().like("product_name", key);
//			}
//		}else{
//			if(userList!=null){
//				String ownerString = "(owner=";
//				 for(int i =0;i<userList.size();i++){
//					 if((i+1)!=userList.size()){
//						 ownerString = ownerString+"'"+userList.get(i).getUsername()+"' ";
//					 }else{
//						 ownerString = ownerString+"'"+userList.get(i).getUsername()+"' or owner= '平台)'";
//					 }
//
//				 }
//				 System.out.println(ownerString);
//				if(key==null|| "".equals(key)){
//					ew.andNew().where("1=1 and "+ownerString+"").and().le("added_date", todayDate).ge("dismounted_date", todayDate).eq("status", "added");
//				}else{
//					ew.andNew().where("1=1 and "+ownerString+"").and().le("added_date", todayDate).ge("dismounted_date", todayDate).eq("status", "added").andNew().like("product_name", key);
//				}
//			}
//		}
//		page = this.selectPage(new Query<ProductEntity>(params).getPage(), ew);
//		return new PageUtils(page);
//	}
    @Override
    public void dismountedProduct(Map<String, Object> params) {
        baseMapper.dismountedProduct(params);
    }

    @Override
    public ProductEntity getProductEntity(String productId) {

        return baseMapper.getProductEntity(productId);
    }

    @Override
    public void checkProduct(Map<String, Object> params) {
        baseMapper.checkProduct(params);
    }

    @Override
    public boolean productCheckDelete(@Param("id") int id) {
        boolean code = baseMapper.productCheckDelete(id);
        return code;
    }

    @Override
    public boolean releaseCheckDelete(@Param("id") int id) {
        boolean code = baseMapper.releaseCheckDelete(id);
        return code;
    }

    @Override
    public boolean submitCheck(@Param("id") int id) {
        boolean flag = baseMapper.submitCheck(id);
        return flag;
    }


    /**
     * 激活期限到期时候激活可测试卡
     * */
//	@Override
//	public Map<String,Object> activateCard() {
//		Map<String,Object> result = new HashMap<>();
//		int success = 0;
//		int fail = 0;
//		EntityWrapper<ProductEntity> ew = new EntityWrapper<>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String str = sdf.format(new Date());
//		ew.eq("activation_Time", str);
//		List<ProductEntity> productList = this.selectList(ew);
//		if(productList!=null){
//			for(ProductEntity productInfo : productList){
//				String pruductId = productInfo.getProductId();
//				//修改对应产品的可测试卡，将其激活
//
//				List<StoreTerminnalDetail> terminalList = baseMapper.getTerminalByProductIdAndStatus(pruductId);
//				if(terminalList!=null){
//					for(StoreTerminnalDetail entity : terminalList){
//						String flag = editTerminalService.editStatus(entity.getIccid(), "ACTIVATED_NAME", "",0);
//						if(flag!=null){
//							entity.setStatus("ACTIVATED_NAME");
//							storeTerminnalDetailService.updateById(entity);
//							success++;
//						}else{
//							fail++;
//						}
//					}
//				}
//			}
//		}
//		result.put("success", success);
//		result.put("fail", fail);
//		return result;
//	}

}
