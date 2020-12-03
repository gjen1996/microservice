package com.glen.product.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.glen.product.entity.ProductEntity;
import com.glen.product.utils.PageUtils;


import java.util.List;
import java.util.Map;

public interface ProductService extends IService<ProductEntity> {

    /**
     * 获取所有的产品
     */
    PageUtils queryPage(String check, JSONObject data, String username);


    /**
     * 修改状态为下架
     */
    void changeStatusToAdded();


    /**
     * 修改状态为下架
     */
    void changeStatusToDismounted();

    /**
     * 获取单个产品
     */
    ProductEntity selectOneProduct(int id);


    /**
     * 产品删除
     */
    boolean deleteProductById(int id);


    /**
     * 产品保存
     */
    boolean saveProduct(ProductEntity productInfo);


    /**
     * 产品修改
     */
    boolean updateProduct(ProductEntity productInfo);


    /**
     * 产品的发布
     */
    void releaseProduct(Map<String, Object> params);


    /**
     * 查询所有产品
     */
    PageUtils querySelectPage(String check, Map<String, Object> params, String username);


    /**
     * 查询产品销售的列表
     * @param userList
     * */


    /**
     * 产品下架
     */
    void dismountedProduct(Map<String, Object> params);

    /**
     * 通过productId获取产品实体
     */
    ProductEntity getProductEntity(String productId);

    /**
     * 产品审核
     */
    void checkProduct(Map<String, Object> params);


    /**
     * 删除产品审核
     *
     * @return
     */
    boolean productCheckDelete(int id);

    /**
     * 删除发布审核
     *
     * @return
     */
    boolean releaseCheckDelete(int id);


    /**
     * 提交产品审核
     */
    boolean submitCheck(int id);


    /**
     * 激活期限到期激活为激活的卡
     * */
//	Map<String, Object> activateCard();

}
