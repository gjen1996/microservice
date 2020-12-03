package com.glen.terminnal.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.glen.terminnal.dao.StoreTerminnalDetailDao;
import com.glen.terminnal.entity.StoreTerminnalDetailEntity;
import com.glen.terminnal.service.StoreTerminnalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RequestMapping("/terminnal")
@RestController
@Slf4j
public class StoreTerminnalDetailController {
    @Autowired
    StoreTerminnalDetailService storeTerminnalDetailService;
    @Autowired
    StoreTerminnalDetailDao storeTerminnalDetailDao;

    /**
     * 获取登陆用户所有产品
     */
    @GetMapping("/changeProduct/getStoreTerminalByIccid/{iccid}")
    public StoreTerminnalDetailEntity getStoreTerminalByIccid(@PathVariable String iccid) {
        log.info("进入这里:" + iccid);
        log.info("getStoreTerminalByIccid:" + storeTerminnalDetailService.getStoreTerminalByIccid(iccid));
        storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
        return storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
    }

    @RequestMapping("/changeProduct/updateAllColumnById")
    public Integer updateAllColumnById(@RequestBody JSONObject data) {
        String newProductId = data.getString("newProductId");
        StoreTerminnalDetailEntity storeTerminnalDetailInfo = new StoreTerminnalDetailEntity();
        storeTerminnalDetailInfo.setProductId(newProductId);
        log.info("进入这里:" + newProductId + "---" + storeTerminnalDetailDao.updateAllColumnById(storeTerminnalDetailInfo));
        return storeTerminnalDetailDao.updateAllColumnById(storeTerminnalDetailInfo);
    }

    //更换设备表中的产品ID
    @RequestMapping("/changeProduct/updateById")
    public Integer updateById(@RequestBody JSONObject data) {
        String iccid = data.get("iccid").toString();
        String productId = data.get("product_id").toString();
        String userId = data.get("user_id").toString();
        String userName = data.get("user_name").toString();
        String nextProductId = data.get("next_product_id").toString();
        Date transferDate = (Date) data.get("transfer_date");
        Date effectiveDate = (Date) data.get("effective_date");
        EntityWrapper<StoreTerminnalDetailEntity> storeEw = new EntityWrapper<>();
        storeEw.eq("user_name", userName).eq("product_id", productId).eq("iccid", iccid);
        StoreTerminnalDetailEntity store = storeTerminnalDetailService.selectOne(storeEw);
        store.setProductId(nextProductId);
        storeTerminnalDetailService.updateById(store);
    }
}