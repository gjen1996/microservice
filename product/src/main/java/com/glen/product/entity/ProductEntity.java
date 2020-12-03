package com.glen.product.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品表单
 */
@TableName("product")
public class ProductEntity implements Serializable {

    private static long serialVersionUID = 1L;
    @TableId
    private int id;
    private String productId;
    private String productName;
    private int billingCycle;


    //套餐模式（月付预付不共享：monthPay，月付后付不共享：prePay，跨月预付不共享：nextMonthPrePay，月付后付共享：monthPayShareHandler）
    private String sharingMode;
    private String owner;
    private BigDecimal terminalPrice;
    private String communicationPlan;
    private BigDecimal originalPrice;
    private String status;

    private BigDecimal discount;
    private BigDecimal price;
    private String description;
    private int productCheck;
    private int releaseCheck;
    private String createUser;


    private BigDecimal dataPercentage;
    private BigDecimal decelerationPercentage;
    private int isDelete;

    private int isDeceleration;
    private int isOverLimite;

    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date activationTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date addedDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dismountedDate;

    private BigDecimal data;
    private BigDecimal dataPrice;

    private String ratePlanName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        ProductEntity.serialVersionUID = serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(int billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getSharingMode() {
        return sharingMode;
    }

    public void setSharingMode(String sharingMode) {
        this.sharingMode = sharingMode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getTerminalPrice() {
        return terminalPrice;
    }

    public void setTerminalPrice(BigDecimal terminalPrice) {
        this.terminalPrice = terminalPrice;
    }

    public String getCommunicationPlan() {
        return communicationPlan;
    }

    public void setCommunicationPlan(String communicationPlan) {
        this.communicationPlan = communicationPlan;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getIsDeceleration() {
        return isDeceleration;
    }

    public void setIsDeceleration(int isDeceleration) {
        this.isDeceleration = isDeceleration;
    }

    public int getIsOverLimite() {
        return isOverLimite;
    }

    public void setIsOverLimite(int isOverLimite) {
        this.isOverLimite = isOverLimite;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductCheck() {
        return productCheck;
    }

    public void setProductCheck(int productCheck) {
        this.productCheck = productCheck;
    }

    public int getReleaseCheck() {
        return releaseCheck;
    }

    public void setReleaseCheck(int releaseCheck) {
        this.releaseCheck = releaseCheck;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getDismountedDate() {
        return dismountedDate;
    }

    public void setDismountedDate(Date dismountedDate) {
        this.dismountedDate = dismountedDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    public BigDecimal getDataPrice() {
        return dataPrice;
    }

    public void setDataPrice(BigDecimal dataPrice) {
        this.dataPrice = dataPrice;
    }

    public String getRatePlanName() {
        return ratePlanName;
    }

    public void setRatePlanName(String ratePlanName) {
        this.ratePlanName = ratePlanName;
    }

    public BigDecimal getDataPercentage() {
        return dataPercentage;
    }

    public void setDataPercentage(BigDecimal dataPercentage) {
        this.dataPercentage = dataPercentage;
    }

    public BigDecimal getDecelerationPercentage() {
        return decelerationPercentage;
    }

    public void setDecelerationPercentage(BigDecimal decelerationPercentage) {
        this.decelerationPercentage = decelerationPercentage;
    }


}
