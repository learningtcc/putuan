package com.advanpro.putuan.model;

/**
 * 作者： Vance
 * 时间： 2016/9/5
 * 描述： 用户跪拜汇总信息.
 */
public class KneelInfoSummary {

    //省 性别或者年龄
    private String key;

    private int kneelCount;

    public KneelInfoSummary(String key, int kneelCount) {
        this.key = key;
        this.kneelCount = kneelCount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getKneelCount() {
        return kneelCount;
    }

    public void setKneelCount(int kneelCount) {
        this.kneelCount = kneelCount;
    }
}
