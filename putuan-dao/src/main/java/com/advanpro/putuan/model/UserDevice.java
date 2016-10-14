package com.advanpro.putuan.model;

/**
 * 作者： Vance
 * 时间： 2016/9/21
 * 描述： ${todo}.
 */
public class UserDevice extends BaseModel {

    private int userId;

    private String deviceId;

    private int deviceUsing;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceUsing() {
        return deviceUsing;
    }

    public void setDeviceUsing(int deviceUsing) {
        this.deviceUsing = deviceUsing;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
