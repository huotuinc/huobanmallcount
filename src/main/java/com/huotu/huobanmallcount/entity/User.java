package com.huotu.huobanmallcount.entity;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;

/**
 * Created by lgh on 2015/10/12.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Hot_UserBaseInfo")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UB_UserID")
    private Integer id;

    /**
     * 用户名
     */
    @Column(nullable = false, name = "UB_UserLoginName")
    private String username;



    @Column(name = "UB_BelongOne")
    private Integer parentId;

    /**
     * 所属商家
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "UB_CustomerID")
    private Merchant merchant;
    /**
     * 手机号
     */
    @Column(name = "UB_UserMobile")
    private String mobile;

    /**
     * 真实姓名
     */
    @Column(name = "UB_UserRealName")
    private String realName;

    /**
     * 微信头像
     */
    @Column(name = "UB_WxHeadImg")
    private String wxHeadUrl;

    /**
     * 微信昵称
     */
    @Column(name = "UB_WxNickName")
    private String wxNickName;

    /**
     * 用户头像
     */
    @Column(name = "UB_UserFace")
    private String userFace;

    /**
     * 余额
     */
    @Column(name = "UB_UserBalance")
    private float balance;

    public User(String username, Integer parentId, Merchant merchant, String mobile, String realName, String wxHeadUrl, String wxNickName, String userFace, float balance) {
        this.username = username;
        this.parentId = parentId;
        this.merchant = merchant;
        this.mobile = mobile;
        this.realName = realName;
        this.wxHeadUrl = wxHeadUrl;
        this.wxNickName = wxNickName;
        this.userFace = userFace;
        this.balance = balance;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getWxHeadUrl() {
        return wxHeadUrl;
    }

    public void setWxHeadUrl(String wxHeadUrl) {
        this.wxHeadUrl = wxHeadUrl;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
