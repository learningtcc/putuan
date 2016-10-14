package com.advanpro.putuan.dao;

import com.advanpro.putuan.model.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
public interface DeviceDao {

    void add(Device device);

    void update(Device Device);

    int count(@Param("deviceIdOrMac") String deviceIdOrMac);

    List<Device> queryByCondition(@Param("deviceIdOrMac") String deviceIdOrMac, @Param("start") int start, @Param("limit") int limit);

    Device queryByMac(String mac);

    Device queryById(int id);

    Device queryByDeviceId(String deviceId);
}
