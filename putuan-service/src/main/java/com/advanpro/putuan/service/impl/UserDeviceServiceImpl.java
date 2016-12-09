package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.UserDao;
import com.advanpro.putuan.dao.UserDeviceDao;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.UserDevice;
import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.service.DeviceService;
import com.advanpro.putuan.service.UserDeviceService;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.advanpro.putuan.utils.wx.TransMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 作者： Vance
 * 时间： 2016/9/27
 * 描述： ${todo}.
 */
@Service
public class UserDeviceServiceImpl implements UserDeviceService {


    @Autowired
    private UserDeviceDao userDeviceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private MpProperty mpProperty;


    @Override
    @Transactional
    public void bindDevice(int userId, String deviceId) {
        //清除之前设备绑定的用户
        userDeviceDao.clear(deviceId);

        UserDevice userDevice = new UserDevice();
        userDevice.setUserId(userId);
        userDevice.setDeviceId(deviceId);
        userDevice.setDeviceUsing(1);
        List<UserDevice> userDeviceList = userDeviceDao.query(userId, deviceId);
        if (userDeviceList != null && !userDeviceList.isEmpty()) {
            userDevice = userDeviceList.get(0);
            userDevice.setDeviceUsing(1);
            userDeviceDao.update(userDevice);
        } else {
            userDeviceDao.add(userDevice);
        }

    }

    @Override
    public void unBindDevice(int userId, String deviceId) {
        userDeviceDao.unBindDevice(userId, deviceId);
    }

    @Override
    public List<UserDevice> query(int userId, String deviceId) {
        return userDeviceDao.query(userId, deviceId);
    }

    @Override
    public List<UserDevice> getDevice(int userId) {
        return userDeviceDao.get(userId);
    }

    @Override
    @Transactional
    public void bindDeviceWX(String openId, String deviceId) {
        User user = userDao.getUserByOpenId(openId);
        deviceService.compelBind(openId, deviceId);

        //如果最近使用账号不是本账号，则清理设备数据
        UserDevice preUserDevice = userDeviceDao.queryByDeviceId(deviceId);
        if (preUserDevice != null && user.getId() != preUserDevice.getUserId()) {
            String content = TransMsgUtil.buildClearMsg();
            String json = "{\"device_type\": \"" + mpProperty.getOriginId() + "\", \"device_id\": \"" + deviceId
                    + "\", \"openid\": \"" + openId + "\", \"content\": \"" + content + "\"}";

            String accessToken = accessTokenService.getAccessToken();
            String url = mpProperty.getMpDeviceTransMsgUrl() + "?access_token=" + accessToken;
            MpApi.postJson(url, json, Map.class);
        }

        bindDevice(user.getId(), deviceId);
    }

    @Override
    @Transactional
    public void unBindDeviceWX(String openId, String deviceId) {
        User user = userDao.getUserByOpenId(openId);
        deviceService.compelUnBind(openId, deviceId);
        userDeviceDao.unBindDevice(user.getId(), deviceId);
    }

    @Override
    public UserDevice queryByDeviceId(String deviceId) {
        return userDeviceDao.queryByDeviceId(deviceId);
    }

    @Override
    public List<UserDevice> queryUsing(int userId, String deviceId) {
        return userDeviceDao.queryUsing(userId, deviceId);
    }

    @Override
    public List<UserDevice> queryUsingByDeviceId(String deviceId) {
        return userDeviceDao.queryUsingByDeviceId(deviceId);
    }
}
