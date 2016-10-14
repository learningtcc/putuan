package com.advanpro.putuan.model;

import java.util.Date;

/**
 * 作者： Vance
 * 时间： 2016/9/19
 * 描述： ${todo}.
 */
public class UserKneelInfo {

    private int userId;

    private String nickName;

    private String headimgUrl;

    private int kneelCount;

    private int ranking;

    private Date beginTime;

    private Date endTime;

    public UserKneelInfo(int userId, String nickName, String headimgUrl, int kneelCount, int ranking, Date beginTime, Date endTime) {
        this.userId = userId;
        this.nickName = nickName;
        this.headimgUrl = headimgUrl;
        this.kneelCount = kneelCount;
        this.ranking = ranking;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public int getKneelCount() {
        return kneelCount;
    }

    public void setKneelCount(int kneelCount) {
        this.kneelCount = kneelCount;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
