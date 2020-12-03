package com.glen.product.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glen.product.entity.ProductEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductDao extends BaseMapper<ProductEntity> {


    void releaseProduct(Map<String, Object> params);

    void changeStatusToAdded();

    void changeStatusToDismounted();

    int isRatePlanUsed(String ratePlanId);

    void dismountedProduct(Map<String, Object> params);

    ProductEntity getProductEntity(String productId);

    void checkProduct(Map<String, Object> params);

    boolean deleteProductById(@Param("id") int id);

    boolean productCheckDelete(@Param("id") int id);

    boolean releaseCheckDelete(@Param("id") int id);

    boolean submitCheck(@Param("id") int id);

    /**
     * 通过iccid获取产品
     */

    ProductEntity getProductInfoByIccid(String iccid);


    /**
     * 激活产品Id对应的可测试卡
     */
    boolean activateCardByProductId(String pruductId);

    /**
     * 根据产品ID跟状态获取要变更的卡列表
     * @author chenz76
     * */
    //public List<StoreTerminnalDetail> getTerminalByProductIdAndStatus(@Param("productId") String productId);

}
