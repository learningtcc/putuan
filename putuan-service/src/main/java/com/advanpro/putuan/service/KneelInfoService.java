package com.advanpro.putuan.service;

import com.advanpro.putuan.dao.query.UserKneelQuery;
import com.advanpro.putuan.model.*;
import com.advanpro.putuan.utils.common.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/5
 * 描述： ${todo}.
 */
public interface KneelInfoService {

    void add(KneelInfo KneelInfo);

    void update(KneelInfo KneelInfo);

    List<KneelInfo> queryByUserId(int userId);

    List<KneelInfo> queryByUserIdAndTime(int userId, Date beginTime, Date endTime);

    List<KneelInfo> queryByTime(Date beginTime, Date endTime);

    List<KneelInfoSummary> getKneelInfoSummary(Date beginTime, Date endTime, String filter);

    List<KneelInfoTrend> getKneelInfoTrend(Date beginTime, Date endTime, String filter);

    Page<KneelInfo> getKneelInfoDetail(UserKneelQuery userKneelQuery);

    List<UserKneelInfo> getKneelInfoRanking(String province, Date beginTime, Date endTime);

    UserKneelInfo getUserKneelInfo(User user, String province, Date beginTime, Date endTime);

    void addOrUpdate(KneelInfo kneelInfo);

    void addOrUpdateBase(BaseKneelInfo baseKneelInfo);

    List<KneelInfo> queryByLastUpdate(int userId, Date lastSyncTime);

    void setHistory(User user, KneelInfo kneelInfo);

    void addOrUpdateWX(int userId, String deviceId, int kneelCount);

    int getKneelCount();
}
