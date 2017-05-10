package com.cc.rubick.model;

import com.google.gson.Gson;

/**
 * Created by bigcong on 27/12/2016.
 */


public class User implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String password;

    private String name;

    private String isLocked;

    private String enableFlag;

    private String sex;

    private String email;

    private String address;

    private String tel;

    private String belongRoles = "";// �û�ӵ�еĽ�ɫ

    private String info;

    // Constructors

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBelongRoles() {
        return belongRoles;
    }

    public void setBelongRoles(String belongRoles) {
        this.belongRoles = belongRoles;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * minimal constructor
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * full constructor
     */
    public User(String userName, String password, String name) {
        this.userName = userName;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    // Property accessors

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}