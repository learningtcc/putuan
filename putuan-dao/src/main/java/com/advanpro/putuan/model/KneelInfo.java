package com.advanpro.putuan.model;

import java.util.Date;

/**
 * 作者： Vance
 * 时间： 2016/9/5
 * 描述： 跪拜信息.
 */
public class KneelInfo extends BaseModel {

    private int userId;

    private String deviceId;

    private int kneelCount;

    private int freeze;

    private Date startTime;

    private User user;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getKneelCount() {
        return kneelCount;
    }

    public void setKneelCount(int kneelCount) {
        this.kneelCount = kneelCount;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
