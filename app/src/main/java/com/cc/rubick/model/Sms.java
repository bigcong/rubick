package com.cc.rubick.model;

/**
 * Created by bigcong on 03/01/2017.
 */

public class Sms {
    /**
     * 主键id
     */
    private Integer smsId;

    /**
     * 短消息序号
     */
    private Integer smsIds;

    /**
     * 对话的序号
     */
    private Integer threadId;

    /**
     * 发件人地址
     */
    private String address;

    /**
     * 不带+86
     */
    private String addressMobile;

    /**
     * 发件人
     */
    private String person;

    /**
     * 日期
     */
    private String date;

    /**
     * 协议 0 SMS_RPOTO, 1 MMS_PROTO
     */
    private String protocol;

    /**
     * 是否阅读 0未读， 1已读
     */
    private Integer read;

    /**
     * 状态 -1接收，0 complete, 64 pending, 128 failed
     */
    private Integer status;

    /**
     * 类型 1是接收到的，2是已发出
     */
    private Integer type;

    /**
     * 短消息内容
     */
    private String body;

    /**
     * 短信服务中心号码编号
     */
    private String serviceCenter;

    /**
     * 拥有者id
     */
    private Integer ownerId;

    /**
     * 拥有者手机
     */
    private String ownerMobile;

    public Integer getSmsId() {
        return smsId;
    }

    public void setSmsId(Integer smsId) {
        this.smsId = smsId;
    }

    public Integer getSmsIds() {
        return smsIds;
    }

    public void setSmsIds(Integer smsIds) {
        this.smsIds = smsIds;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressMobile() {
        return addressMobile;
    }

    public void setAddressMobile(String addressMobile) {
        this.addressMobile = addressMobile;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

}
