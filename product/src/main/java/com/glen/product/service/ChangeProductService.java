package com.glen.product.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.glen.product.entity.ChangeProductEntity;
import com.glen.product.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface ChangeProductService extends IService<ChangeProductEntity> {

    List<String> changePrePayToPrePay(String newProductId, String[] iccids);

    List<String> changeNextMonthPrePayToNextMonthPrePay(String newProductId, String[] iccids);

    List<String> changeMonthPayShareToMonthPayShare(String newProductId, String[] iccids);

    List<String> changeMonthPayToMonthPay(String newProductId, String[] iccids);

    /**
     * 页面展示
     *
     * @author hesw
     */
    PageUtils queryPage(JSONObject data) throws Exception;


}
