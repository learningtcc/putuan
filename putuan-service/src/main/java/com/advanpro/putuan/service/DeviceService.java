package com.advanpro.putuan.service;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.utils.common.Page;

/**
 * 作者： Vance
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
public interface DeviceService {

    Device queryById(int id);

    void compelBind(String openid, String deviceId);

    Page<Device> list(String deviceIdOrMac, int pageNo, int pageSize);

    String add(Device device);

    void update(Device device);

    Device queryByMac(String mac);

    Device queryByDeviceId(String deviceId);

    Page<Device> queryByCondition(String deviceIdOrMac, int pageNo, int pageSize);

    void compelUnBind(String openId, String deviceId);

    Device queryByNo(String deviceNumber);

    Device queryByQRTicket(String ticket);
}