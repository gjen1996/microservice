package com.glen.product.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glen.product.entity.ChangeProductEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ChangeProductDao extends BaseMapper<ChangeProductEntity> {
    Map<Object, Object> getJasperProductStoreInfoByIccid(String iccid);

    /**
     * 根据产品类型选择获取不同产品
     *
     * @param username
     * @param sharingMode
     */
    List<Map<String, Object>> getSelectProductType(@Param("sharingMode") String sharingMode, @Param("username") String username);

}
