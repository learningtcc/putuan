package com.advanpro.putuan.dao;

import com.advanpro.putuan.model.UserDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/21
 * 描述： ${todo}.
 */
public interface UserDeviceDao {
    void add(UserDevice userDevice);

    List<UserDevice> query(@Param("userId") int userId, @Param("deviceId") String deviceId);

    void update(UserDevice userDevice);

    void unBindDevice(@Param("userId")  int userId, @Param("deviceId")  String deviceId);

    List<UserDevice> get(int userId);

    void clear(String deviceId);

    UserDevice queryByDeviceId(String deviceId);
}
