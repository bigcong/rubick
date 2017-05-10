package com.cc.rubick.model;

/**
 * Created by bigcong on 03/01/2017.
 */

public class RecordEntity {

    Integer callId;

    Integer isRead;

    String name;

    String number;

    Integer type;

    String date;

    Long duration;

    Integer callNew;

    Integer callCount;

    String cachedPhotoUri;

    String ownerMobile;

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getCallNew() {
        return callNew;
    }

    public void setCallNew(Integer callNew) {
        this.callNew = callNew;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public String getCachedPhotoUri() {
        return cachedPhotoUri;
    }

    public void setCachedPhotoUri(String cachedPhotoUri) {
        this.cachedPhotoUri = cachedPhotoUri;
    }

}
