package com.advanpro.putuan.service;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.utils.common.Page;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/9/27
 * 描述： ${todo}.
 */
public interface UserDeviceService {

    void bindDevice(int userId, String deviceId);

    void unBindDevice(int userId, String deviceId);

    List<UserDevice> query(int userId, String deviceId);

    List<UserDevice> getDevice(int userId);

    void bindDeviceWX(String openId, String deviceId);

    void unBindDeviceWX(String openId, String deviceId);
}