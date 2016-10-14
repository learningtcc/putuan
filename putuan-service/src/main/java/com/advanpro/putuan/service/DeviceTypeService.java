package com.advanpro.putuan.service;

import com.advanpro.putuan.model.DeviceType;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
public interface DeviceTypeService {

    DeviceType queryById(int id);

    void add(DeviceType deviceType);

    void update(DeviceType deviceType);

    List<DeviceType> queryAll();
}