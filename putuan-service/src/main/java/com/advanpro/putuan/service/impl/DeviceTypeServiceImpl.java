package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.DeviceTypeDao;
import com.advanpro.putuan.model.DeviceType;
import com.advanpro.putuan.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者： Vance
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {

    @Autowired
    private DeviceTypeDao deviceTypDao;

    @Override
    public DeviceType queryById(int id) {
        return deviceTypDao.queryById(id);
    }

    @Override
    @Transactional
    public void add(DeviceType deviceType) {
        deviceTypDao.add(deviceType);
    }

    @Override
    @Transactional
    public void update(DeviceType deviceType) {
        deviceTypDao.update(deviceType);
    }

    @Override
    public List<DeviceType> queryAll() {
        return deviceTypDao.queryAll();
    }
}
