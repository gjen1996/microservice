package com.glen.billing.entity;

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
 * @author chenkb3
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("months_prepay_card")
public class MonthsPrepayCardEntity extends Model<MonthsPrepayCardEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String iccid;
    /**
     * 单卡跨月预付当前周期开始计费时间
     */
    @TableField("cycle_begin")
    private Date cycleBegin;
    /**
     * 周期天数
     */
    @TableField("cycle_day")
    private Integer cycleDay;
    /**
     * 当前周期结束时间
     */
    @TableField("cycle_end")
    private Date cycleEnd;
    /**
     * 当前用量
     */
    @TableField("cycle_usage")
    private BigDecimal cycleUsage;
    /**
     * 存放周期中各月流量使用情况
     */
    @TableField("history_usage")
    private String historyUsage;
    /**
     * 流量修正值，临界修正值
     */
    @TableField("minus_value")
    private BigDecimal minusValue;
    /**
     * 第几个周期，初始周期为1
     */
    @TableField("cycle_id")
    private Integer cycleId;
    /**
     * 用来标记该卡当前是否计费。
     * 0：初始状态，还在算流量
     * 1：当前周期已经结束，可以进行计费
     * 2：已经计费出账
     */
    @TableField("fee_status")
    private Integer feeStatus;
    /**
     * 记录计费后的时间
     */
    @TableField("fee_time")
    private Date feeTime;

    @TableField("product_id")
    private String productId;

    @TableField("account_id")
    private String accountId;

    @TableField("user_id")
    private String userId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
