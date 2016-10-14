package com.advanpro.putuan.model;

import java.util.Date;

/**
 * 作者： Vance
 * 时间： 2016/9/5
 * 描述： 跪拜基数.
 */
public class BaseKneelInfo extends BaseModel {

    private int userId;

    private String deviceId;

    private int count;

    private Date time;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
