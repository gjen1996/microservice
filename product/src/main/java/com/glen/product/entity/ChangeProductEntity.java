package com.glen.product.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("product_change")
public class ChangeProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String username;
    private String iccid;
    private String accountId;
    private String cycleMonth;//开始使用当前产品的计费周期
    private String productId;//当前正在使用的产品
    private String newProductId;//客户要变更的产品Id，在下一阶段将要变更的产品，若无变更则为空
    private Date effectiveDate;//产品变更生效时间
    private int isUpdate;
    private Date createDate;
}
