package com.glen.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.glen.product.dao.ChangeProductDao;
import com.glen.product.methodservice.AppcustomerLoginService;
import com.glen.product.service.ChangeProductService;
import com.glen.product.utils.PageUtils;
import com.glen.product.utils.R;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/product")
@RestController
@Slf4j
public class ChangeProductController {
    @Autowired
    ChangeProductService changeProductService;
    @Autowired
    ChangeProductDao changeProductDao;
    @Autowired
    AppcustomerLoginService appcustomerLoginService;

    /**
     * 获取登陆用户所有产品
     */
    @RequestMapping("/changeProduct")
    @ResponseBody
    public JSONObject changeProduct(@RequestBody JSONObject data) {
        String result = null;
        JSONObject jSONObject = null;
        String sharingMode = data.getString("sharingMode");
        String newProductId = data.getString("newProductId");
        String iccids = data.getString("iccid");
        log.info("进入这里:" + sharingMode + "---" + newProductId + "---" + iccids);

        String[] iccidList = iccids.split(",");
        boolean code = true;
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<>();
        switch (sharingMode) {
            case "prePay":
                list = changeProductService.changePrePayToPrePay(newProductId, iccidList);
                break;
            case "monthPay":
                list = changeProductService.changeMonthPayToMonthPay(newProductId, iccidList);
                break;
            case "monthPayShare":
                list = changeProductService.changeMonthPayShareToMonthPayShare(newProductId, iccidList);
                break;
            case "nextMonthPrePay":
                list = changeProductService.changeNextMonthPrePayToNextMonthPrePay(newProductId, iccidList);
                break;
            default:
                break;
        }
        if (list == null || list.isEmpty() || list.size() == 0) {
            jSONObject.put("code", code);
            jSONObject.put("errorList", list);
        } else {
            jSONObject.put("code", false);
            jSONObject.put("errorList", list);
        }
        return jSONObject;
    }

    /**
     * 获取登陆用户可变更的产品
     */
    @RequestMapping("/selectProductList")
    @ResponseBody
    public JSONArray selectProductList(@RequestBody JSONObject data) {
        JSONObject getToken = appcustomerLoginService.getToken();
        String username = getToken.getString("username");
        log.info("getToken--" + getToken);
        log.info("username:" + username);
        String sharingMode = data.getString("sharingMode");
        List<Map<String, Object>> selectProductList = changeProductDao.getSelectProductType(sharingMode, username);
        JSONArray result = JSONArray.fromObject(selectProductList);
        return result;
    }

    //页面展示
    @RequestMapping("/EditProductQueryPage")
    @ResponseBody
    public JSONObject queryPendingOrder(@RequestBody JSONObject data) throws Exception {
        PageUtils page = changeProductService.queryPage(data);
        return R.ok().put("page", page);
    }
}