package com.glen.terminnal.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wemoucy
 * @since 2019-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_terminnal_detail")
public class StoreTerminnalDetailEntity extends Model<StoreTerminnalDetailEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 供货单号
     */
    @TableField("order_id")
    private String orderId;
    /**
     * 入库编号
     */
    @TableField("stock_id")
    private String stockId;
    /**
     * 入库日期
     */
    @TableField("stock_date")
    private Date stockDate;
    /**
     * 入库人员
     */
    @TableField("stock_person")
    private String stockPerson;
    /**
     * 卡号
     */
    private String iccid;
    private String imsi;
    private String msisdn;
    private String imei;
    /**
     * 卡状态
     */
    private String status;
    /**
     * 资费计划
     */
    @TableField("rate_plan")
    private String ratePlan;
    /**
     * 通信计划
     */
    @TableField("communication_plan")
    private String communicationPlan;
    /**
     * 卡用户id
     */
    @TableField("user_id")
    private String userId;
    /**
     * 卡用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 产品编码
     */
    @TableField("product_id")
    private String productId;
    /**
     * 激活日期
     */
    @TableField("date_activated")
    private Date dateActivated;
    /**
     * 是否划拨
     */
    @TableField("is_transfer")
    private String isTransfer;
    /**
     * 划拨人员
     */
    @TableField("transfer_people")
    private String transferPeople;
    /**
     * 划拨日期
     */
    @TableField("transfer_date")
    private Date transferDate;
    /**
     * 订单日期
     */
    @TableField("sale_date")
    private Date saleDate;
    /**
     * 订单流水号
     */
    @TableField("sale_num")
    private String saleNum;

    @TableField("jasper_username")
    private String jasperUsername;
    /**
     * 套餐模式（月付预付不共享：monthPay，月付后付不共享：prePay，跨月预付不共享：nextMonthPrePay，月付后付共享：monthPayShareHandler）
     */
    @TableField("sharing_mode")
    private String sharingMode;
    /**
     * 下个月是否加入（1：是，0：不是）
     */
    @TableField("next_month_join")
    private Integer nextMonthJoin;
    /**
     * 订单状态 未发货（1）、已发货（2）、已收货（3）
     */
    @TableField("order_status")
    private String orderStatus;

    @TableField("month_join")
    private Integer monthJoin;

    @TableField("account_id")
    private String accountId;

    @TableField("jasper_rate_plan")
    private String jasperRatePlan;

    /**
     * 是否手动激活
     */
    @TableField("is_manual")
    private Integer isManual;

    @TableField(exist = false)
    private BigDecimal monthToDateUsage;

    @TableField(exist = false)
    private String productName;

    @TableField(exist = false)
    private String idForProductShow;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
