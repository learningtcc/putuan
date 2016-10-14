package com.advanpro.putuan.api.form;

import com.advanpro.putuan.model.KneelInfo;

import java.util.Date;

/**
 * 作者： Vance
 * 时间： 2016/9/21
 * 描述： ${todo}.
 */
public class KneelInfoVo {
    private int id;

    private int userId;

    private int count;

    private String deviceId;

    private long time;

    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public KneelInfo toKneelInfo() {
        KneelInfo kneelInfo = new KneelInfo();
        kneelInfo.setId(this.getId());
        kneelInfo.setUserId(this.getUserId());
        kneelInfo.setDeviceId(this.getDeviceId());
        kneelInfo.setKneelCount(this.getCount());
        kneelInfo.setStartTime(new Date(this.getTime()));
        return kneelInfo;
    }

    public void populateKneelInfo(KneelInfo kneelInfo) {
        this.setUserId(kneelInfo.getUserId());
        this.setCount(kneelInfo.getKneelCount());
        this.setId(kneelInfo.getId());
        this.setDeviceId(kneelInfo.getDeviceId());
        this.setTime(kneelInfo.getStartTime().getTime());
        this.setUpdateTime(kneelInfo.getUpdateTime().getTime());
    }
}
