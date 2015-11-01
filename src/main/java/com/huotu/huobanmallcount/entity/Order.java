package com.huotu.huobanmallcount.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单
 * 说明：对应表 Mall_Orders 实体 OrdersModel
 * Created by lgh on 2015/8/26.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Mall_Orders")
public class Order {
    /**
     * 订单号(yyyyMMdd+8位随机数)
     */
    @Id
    @Column(name = "Order_Id",length = 20)
    private String id;

    /**
     * 所属商家
     */
    @ManyToOne
    @JoinColumn(name = "Customer_Id")
    private Merchant merchant;

    /**
     * 用户
     */
    @Column(name = "Member_Id")
    private Integer userId;

    /**
     * 用户类型 0普通会员1小伙伴
     */
    @Column(name = "Rel_UserType")
    private Integer userType;

    /**
     * 订单名称
     */
    @Column(name = "Tostr")
    private String title;



    /**
     * 订单状态 0活动 -1死单 1已完成
     */
    @Column(name = "Status")
    private Integer status;

    /**
     * 付款状态  0：未支付|1：已支付|2：已支付至担保方|3：部分付款|4：部分退款|5：全额退款
     */
    @Column(name = "Pay_Status")
    private Integer payStatus;


    /**
     * 发货状态
     * 0：未发货|1：已发货|2：部分发货|3：部分退货|4：已退货
     */
    @Column(name = "Ship_Status")
    private Integer deliverStatus;

    /**
     * 收货人
     */
    @Column(name = "Ship_Name")
    private String receiver;

    /**
     * 商品数量
     */
    @Column(name = "Itemnum")
    private Integer amount;
    /**
     * 订单总金额
     */
    @Column(name = "Final_Amount")
    private float price;


    /**
     * 下单时间 按照此排序
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "Createtime")
    private Date time;


    /**
     * 付款时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "Paytime")
    private Date payTime;

    /**
     * 是否开发票
     */
    @Column(nullable = false,name = "Is_Tax")
    private Integer isTax;



    /**
     * 是否保价
     */
    @Column(nullable = false,name = "Is_Protect")
    private  Integer isProtect;

    /**
     * 接收状态    1为签收
     */
    @Column(name = "Rel_receiveStatus")
    private Integer receivestatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(Integer deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public Integer getIsTax() {
        return isTax;
    }

    public void setIsTax(Integer isTax) {
        this.isTax = isTax;
    }

    public Integer getIsProtect() {
        return isProtect;
    }

    public void setIsProtect(Integer isProtect) {
        this.isProtect = isProtect;
    }


    public Integer getReceivestatus() {
        return receivestatus;
    }

    public void setReceivestatus(Integer receivestatus) {
        this.receivestatus = receivestatus;
    }
}
