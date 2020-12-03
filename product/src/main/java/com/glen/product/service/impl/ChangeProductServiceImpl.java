package com.glen.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glen.product.entity.ChangeProductEntity;
import com.glen.product.dao.ChangeProductDao;
import com.glen.product.entity.ProductEntity;
import com.glen.product.methodservice.AppcustomerLoginService;
import com.glen.product.service.ChangeProductService;
import com.glen.product.methodservice.MonthsPrepayCardService;
import com.glen.product.methodservice.StoreTerminnalDetailService;
import com.glen.product.service.ProductService;
import com.glen.product.utils.PageUtils;
import com.glen.product.utils.Query;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.NewThreadAction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ChangeProductServiceImpl extends ServiceImpl<ChangeProductDao, ChangeProductEntity>
        implements ChangeProductService {

    @Autowired
    private StoreTerminnalDetailService storeTerminnalDetailService;
    @Autowired
    private MonthsPrepayCardService monthsPrepayCardService;
    @Autowired
    ProductService productService;
    @Autowired
    AppcustomerLoginService appcustomerLoginService;

    //预付到预付
    @Override
    public List<String> changePrePayToPrePay(String newProductId, String[] iccids) {
        List<String> errorList = new ArrayList<>();
        for (int i = 0; i < iccids.length; i++) {
            String iccid = iccids[i];
            //2.获取卡
            log.info("iccic" + iccid);
            Map<String, Object> storeTerminnalDetailInfo = storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
            if (storeTerminnalDetailInfo.get("message") != null) {
                errorList.add("" + storeTerminnalDetailInfo.get("message"));
            } else {
                log.info("storeTerminnalDetailInfo:" + storeTerminnalDetailInfo);
                log.info("storeTerminnalDetailInfo:" + storeTerminnalDetailInfo.get("id"));
                boolean flag = this.updateChangeProduct(storeTerminnalDetailInfo, storeTerminnalDetailInfo.get("productId").toString(), newProductId);
                if (flag == false) {
                    errorList.add(iccid);
                }
            }
        }
        return errorList;
    }

    //跨月预付到跨月预付
    @Override
    public List<String> changeNextMonthPrePayToNextMonthPrePay(String newProductId, String[] iccids) {
        List<String> errorList = new ArrayList<>();
        for (int i = 0; i < iccids.length; i++) {
            String iccid = iccids[i];
            //2.获取卡
            Map<String, Object> storeTerminnalDetailInfo = storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
            if (storeTerminnalDetailInfo.get("message") != null) {
                errorList.add("" + storeTerminnalDetailInfo.get("message"));
            } else {
                log.info("storeTerminnalDetailInfo:" + storeTerminnalDetailInfo);
                log.info("storeTerminnalDetailInfo:" + storeTerminnalDetailInfo.get("id"));
                boolean flag = this.updateMonthPrePay(storeTerminnalDetailInfo, storeTerminnalDetailInfo.get("productId").toString(), newProductId);
                if (flag == false) {
                    errorList.add(iccid);
                }
            }
        }
        return errorList;
    }

    //后付到后付
    @Override
    public List<String> changeMonthPayToMonthPay(String newProductId, String[] iccids) {
        List<String> errorList = new ArrayList<>();
        for (int i = 0; i < iccids.length; i++) {
            String iccid = iccids[i];
            //2.获取卡
            Map<String, Object> storeTerminnalDetailInfo = storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
            if (storeTerminnalDetailInfo.get("message") != null) {
                errorList.add("" + storeTerminnalDetailInfo.get("message"));
            } else {
                //2.获取卡信息
                Map<Object, Object> map = baseMapper.getJasperProductStoreInfoByIccid(iccid);
                if (map != null) {
                    BigDecimal iccidBalance = (BigDecimal) map.get("iccidBalance");
                    if (iccidBalance.compareTo(new BigDecimal("0")) < 0) {
                        errorList.add("iccid:" + iccid + "错误原因：当前卡欠费");
                    } else {
                        BigDecimal monthToDateDataUsage = (BigDecimal) map.get("monthToDateDataUsage");

                        //3.获取新产品信息
                        ProductEntity newProductInfo = productService.getProductEntity(newProductId);
                        int isOverLimite = newProductInfo.getIsOverLimite();
                        JSONObject json = new JSONObject();
                        json.put("newProductId", newProductId);
                        String productId = storeTerminnalDetailInfo.get("productId").toString();
                        if (isOverLimite == 0) {
                            //不允许超套
                            BigDecimal dataPercentage = newProductInfo.getDataPercentage();
                            BigDecimal data = newProductInfo.getData();
                            if (monthToDateDataUsage.compareTo(data.multiply(dataPercentage)) > 0) {
                                //流量超过
                                errorList.add("iccid:" + iccid + "错误原因：当前卡使用的流量大于变更的产品流量");
                            } else {
                                //流量未超过,执行变更
                                //store_terminnal_details更新
                                Integer flag = storeTerminnalDetailService.updateAllColumnById(json);
                                //插入一条日志，更新一条日志
                                this.changeProductNow(storeTerminnalDetailInfo, productId, newProductId);
                                //次月要生效的产品
                                this.updateChangeProductMonthPay(storeTerminnalDetailInfo, newProductId, productId);
                            }
                        } else {
                            //执行变更
                            //store_terminnal_details更新
                            Integer flag = storeTerminnalDetailService.updateAllColumnById(json);
                            //插入一条日志，更新一条日志
                            this.changeProductNow(storeTerminnalDetailInfo, productId, newProductId);
                            this.updateChangeProductMonthPay(storeTerminnalDetailInfo, newProductId, productId);
                        }
                    }
                } else {
                    errorList.add("iccid:" + iccid + "错误原因：未能找到此卡，请联系管理员！");
                }
            }
        }
        return errorList;
    }


    //后付共享到后付共享
    @Override
    public List<String> changeMonthPayShareToMonthPayShare(String newProductId, String[] iccids) {
        List<String> errorList = new ArrayList<>();
        for (int i = 0; i < iccids.length; i++) {
            String iccid = iccids[i];
            //2.获取卡
            Map<String, Object> storeTerminnalDetailInfo = storeTerminnalDetailService.getStoreTerminalByIccid(iccid);
            if (storeTerminnalDetailInfo.get("message") != null) {
                errorList.add("" + storeTerminnalDetailInfo.get("message"));
            } else {
                int monthJoin = Integer.parseInt(storeTerminnalDetailInfo.get("monthJoin").toString());
                String productId = storeTerminnalDetailInfo.get("productId").toString();
                JSONObject json = new JSONObject();
                json.put("newProductId", newProductId);
                if (monthJoin == 1) {
                    //本月加入，则次月改产品
                    //插入一条次月生效日志
                    boolean flag = this.updateChangeProduct(storeTerminnalDetailInfo, productId, newProductId);
                    if (flag == false) {
                        errorList.add("iccid:" + iccid + "错误原因：数据更新失败！");
                    }
                } else {
                    //本月未加入，则立即改产品
                    Integer flag = storeTerminnalDetailService.updateAllColumnById(json);
                    //插入一条日志
                    boolean flag2 = this.changeProductNow(storeTerminnalDetailInfo, productId, newProductId);
                    if (flag2 == false) {
                        errorList.add("iccid:" + iccid + "错误原因：数据更新失败！");
                    }
                }
            }
        }
        return errorList;
    }

    /**
     * 插入或更新，一条次月生效日志
     *
     * @param productId
     */
    public boolean updateChangeProduct(Map<String, Object> storeTerminnalDetailInfo, String productId, String newProductId) {
        String cycleMonth = this.getYearAndMonth();
        String iccid = storeTerminnalDetailInfo.get("iccid").toString();
        String username = storeTerminnalDetailInfo.get("userName").toString();
        String userId = storeTerminnalDetailInfo.get("userId").toString();
        String accountId = storeTerminnalDetailInfo.get("accountId").toString();
        //获取ChangeProductEntity
        EntityWrapper<ChangeProductEntity> ew = new EntityWrapper<>();
        ew.andNew().eq("iccid", iccid).eq("cycle_month", cycleMonth).eq("is_update", 0);
        ChangeProductEntity changeProductEntity = this.selectOne(ew);
        if (changeProductEntity == null) {
            changeProductEntity = new ChangeProductEntity();
        }
        changeProductEntity.setIccid(iccid);
        changeProductEntity.setNewProductId(newProductId);
        changeProductEntity.setAccountId(accountId);
        changeProductEntity.setUserId(userId);
        changeProductEntity.setUsername(username);
        changeProductEntity.setProductId(productId);
        changeProductEntity.setIsUpdate(0);
        changeProductEntity.setCycleMonth(cycleMonth);
        ;
        changeProductEntity.setCreateDate(new Date());
        changeProductEntity.setEffectiveDate(this.getYearMonthAndDay());
        this.insertOrUpdate(changeProductEntity);
        return true;
    }

    public boolean updateMonthPrePay(Map<String, Object> storeTerminnalDetailInfo, String productId, String newProductId) {

        String cycleMonth = this.getYearAndMonth();
        String iccid = storeTerminnalDetailInfo.get("iccid").toString();
        String username = storeTerminnalDetailInfo.get("userName").toString();
        String userId = storeTerminnalDetailInfo.get("userId").toString();
        String accountId = storeTerminnalDetailInfo.get("accountId").toString();
        Date effectDate = null;
        JSONObject data = new JSONObject();
        data.put("iccid", iccid);
        String effectDateStr = monthsPrepayCardService.changePlanTime(data);
        log.info("effectDateStr:" + effectDateStr);
        if (effectDateStr.equals("error")) {
            return false;
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                effectDate = sdf.parse(effectDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            log.info("effectDateStr:" + effectDate);
            if (effectDate != null) {
                //获取ChangeProductEntity
                EntityWrapper<ChangeProductEntity> ew = new EntityWrapper<>();
                ew.andNew().eq("iccid", iccid).eq("is_update", 0);
                ew.andNew().gt("effective_date", new Date());
                ChangeProductEntity changeProductEntity = this.selectOne(ew);
                if (changeProductEntity == null) {
                    changeProductEntity = new ChangeProductEntity();
                }
                changeProductEntity.setIccid(iccid);
                changeProductEntity.setNewProductId(newProductId);
                changeProductEntity.setAccountId(accountId);
                changeProductEntity.setUserId(userId);
                changeProductEntity.setUsername(username);
                changeProductEntity.setProductId(productId);
                changeProductEntity.setIsUpdate(0);
                changeProductEntity.setCycleMonth(cycleMonth);
                ;
                changeProductEntity.setCreateDate(new Date());
                changeProductEntity.setEffectiveDate(effectDate);
                this.insertOrUpdate(changeProductEntity);
                return true;
            } else {
                return false;
            }
        }
    }

    public String getYearAndMonth() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH);//获取月份
        month += 1;
        int day = cal.get(Calendar.DATE);//获取日
        String stringMonth = "";
        if (day > 26) {
            month += 1;
        }
        if (month > 12) {
            year += 1;
        }
        if (month < 10) {
            stringMonth = "0" + month;
        }
        return year + "-" + stringMonth;
    }

    public Date getYearMonthAndDay() {
        Date cycle = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String billingCycle = sdf.format(cycle) + "-27";
        Date result = null;
        try {
            result = simpleDateFormat.parse(billingCycle);
        } catch (Exception e) {
            System.out.println("时间获取失败");
        }
        return result;
    }

    /**
     * 插入立即生效日志
     */
    public boolean changeProductNow(Map<String, Object> storeTerminnalDetailInfo, String productId, String newProductId) {
        String cycleMonth = this.getYearAndMonth();
        String iccid = storeTerminnalDetailInfo.get("iccid").toString();
        String username = storeTerminnalDetailInfo.get("userName").toString();
        String userId = storeTerminnalDetailInfo.get("userId").toString();
        String accountId = storeTerminnalDetailInfo.get("accountId").toString();
        //插入一条ChangeProductEntity,设置为已生效
        ChangeProductEntity changeProductInfo = new ChangeProductEntity();
        changeProductInfo.setIccid(iccid);
        changeProductInfo.setUserId(userId);
        changeProductInfo.setProductId(productId);
        changeProductInfo.setNewProductId(newProductId);
        changeProductInfo.setAccountId(accountId);
        changeProductInfo.setCycleMonth(cycleMonth);
        changeProductInfo.setEffectiveDate(new Date());
        changeProductInfo.setCreateDate(new Date());
        changeProductInfo.setIsUpdate(1);
        changeProductInfo.setUsername(username);
        this.insert(changeProductInfo);
        return true;
    }

    /**
     * 针对月付后付不共享的产品
     */
    public boolean updateChangeProductMonthPay(Map<String, Object> storeTerminnalDetailInfo, String productId, String newProductId) {
        String cycleMonth = this.getYearAndMonth();
        String iccid = storeTerminnalDetailInfo.get("iccid").toString();
        String username = storeTerminnalDetailInfo.get("userName").toString();
        String userId = storeTerminnalDetailInfo.get("userId").toString();
        String accountId = storeTerminnalDetailInfo.get("accountId").toString();
        //获取ChangeProductEntity
        EntityWrapper<ChangeProductEntity> ew = new EntityWrapper<>();
        ew.andNew().eq("iccid", iccid).eq("cycle_month", cycleMonth).eq("is_update", 0);
        ChangeProductEntity changeProductEntity = this.selectOne(ew);
        if (changeProductEntity == null) {
            changeProductEntity = new ChangeProductEntity();
            changeProductEntity.setIccid(iccid);
            changeProductEntity.setNewProductId(newProductId);
            changeProductEntity.setAccountId(accountId);
            changeProductEntity.setUserId(userId);
            changeProductEntity.setUsername(username);
            changeProductEntity.setProductId(productId);
            changeProductEntity.setIsUpdate(0);
            changeProductEntity.setCycleMonth(cycleMonth);
            ;
            changeProductEntity.setCreateDate(new Date());
            changeProductEntity.setEffectiveDate(this.getYearMonthAndDay());
        } else {
            changeProductEntity.setIccid(iccid);
            changeProductEntity.setAccountId(accountId);
            changeProductEntity.setUserId(userId);
            changeProductEntity.setUsername(username);
            changeProductEntity.setProductId(productId);
            changeProductEntity.setIsUpdate(0);
            changeProductEntity.setCycleMonth(cycleMonth);
            ;
            changeProductEntity.setCreateDate(new Date());
            changeProductEntity.setEffectiveDate(this.getYearMonthAndDay());
        }
        this.insertOrUpdate(changeProductEntity);
        return true;
    }

    @Override
    public PageUtils queryPage(JSONObject data) throws Exception {
        Map<String, Object> getToken = appcustomerLoginService.getToken();
        String showDataType = data.getString("showDataType");
        String key = data.getString("key");
        String username = getToken.get("username").toString();
        String userList = data.getString("userList");
        JSONArray jsonArray = JSONArray.fromObject(userList);
        Long userId = Long.parseLong(getToken.get("userId").toString());
        Page<ChangeProductEntity> page = null;
        EntityWrapper<ChangeProductEntity> ew = new EntityWrapper<>();
        if (showDataType.equals("total")) {
            if (StringUtils.isNotBlank(key)) {
                ew.like("user_id", key).or().like("username", key).or().like("iccid", key).or().like("account_id", key);
            }
        }

        if (showDataType.equals("personal")) {
            ew.eq("user_id", userId);
            if (StringUtils.isNotBlank(key)) {
                ew.andNew().like("user_id", key).or().like("username", key).or().like("iccid", key).or().like("account_id", key);
                ;
            }
        }

        if (showDataType.equals("someUser")) {
            if (!jsonArray.isEmpty()) {
                if (key == null || key.equals("")) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        ew.or().eq("user_id", jsonArray.getJSONObject(i).get("userId"));
                    }
                } else {
                    ew.and().like("user_id", key).or().like("username", key).or().like("iccid", key).or().like("account_id", key);
                    ;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        if (i == 0) {
                            ew.andNew().eq("user_id", jsonArray.getJSONObject(i).get("userId"));
                        } else {
                            ew.or().eq("user_id", jsonArray.getJSONObject(i).get("userId"));
                        }
                    }

                }
            } else {
                ew.and().eq("1", "2");
            }

        }
        Map<String, Object> params = new HashMap<>();
        page = this.selectPage(new Query<ChangeProductEntity>(params).getPage(), ew);
        log.info("page:" + page);
        return new PageUtils(page);
    }
}
