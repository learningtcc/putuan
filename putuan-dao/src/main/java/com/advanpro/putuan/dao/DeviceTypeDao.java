package com.advanpro.putuan.dao;

import com.advanpro.putuan.model.DeviceType;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/8/31
 * 描述： ${todo}.
 */

public interface DeviceTypeDao {

    void add(DeviceType deviceType);

    void update(DeviceType DeviceType);

    DeviceType queryById(int id);

    List<DeviceType> queryAll();
}