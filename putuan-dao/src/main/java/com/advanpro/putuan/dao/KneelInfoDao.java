package com.advanpro.putuan.dao;

import com.advanpro.putuan.dao.query.UserKneelQuery;
import com.advanpro.putuan.model.BaseKneelInfo;
import com.advanpro.putuan.model.KneelInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * 作者： Vance
 * 时间： 2016/9/5
 * 描述： ${todo}.
 */
public interface KneelInfoDao {

    void add(KneelInfo kneelInfo);

    void update(KneelInfo kneelInfo);

    List<KneelInfo> queryByUserIdAndTime(@Param("userId") int userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<KneelInfo> queryByUserIdAndDeviceId(@Param("userId") int userId, @Param("deviceId") String deviceId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<BaseKneelInfo> queryBase(@Param("userId") int userId, @Param("deviceId") String deviceId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<KneelInfo> queryByTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<KneelInfo> queryByCondition(UserKneelQuery userKneelQuery);

    Integer countByCondition(UserKneelQuery userKneelQuery);

    List<KneelInfo> queryByLastUpdate(@Param("userId") int userId, @Param("lastSyncTime") Date lastSyncTime);

    void addBase(BaseKneelInfo baseKneelInfo);

    void updateBase(BaseKneelInfo updateBaseKneelInfo);
}
