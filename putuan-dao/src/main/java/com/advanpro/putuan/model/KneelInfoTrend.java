package com.advanpro.putuan.model;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/19
 * 描述： 用户跪拜信息.
 */
public class KneelInfoTrend {

    //省 性别或者年龄
    private String key;

    private List<Integer> kneelCounts;

    public KneelInfoTrend(String key, List<Integer> kneelCounts) {
        this.key = key;
        this.kneelCounts = kneelCounts;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Integer> getKneelCounts() {
        return kneelCounts;
    }

    public void setKneelCounts(List<Integer> kneelCounts) {
        this.kneelCounts = kneelCounts;
    }
}
