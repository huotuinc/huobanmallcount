package com.huotu.huobanmallcount.entity;


import javax.persistence.*;

/**
 * 商家
 * 说明：对应表 Swt_CustomerManage 实体类UserInfoModel
 * Created by lgh on 2015/8/26.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Swt_CustomerManage")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SC_UserID")
    private Integer id;


    /**
     * 登录名
     */
    @Column(name = "SC_UserLoginName")
    private String name;


    /**
     * 密码
     */
    @Column(name = "SC_UserLoginPassword")
    private String password;


    /**
     * 昵称
     */
    @Column(name = "SC_UserNickName")
    private String nickName;







    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }





}
