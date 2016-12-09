package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.dao.DeviceDao;
import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.MpDeviceInfo;
import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.service.DeviceService;
import com.advanpro.putuan.utils.common.Page;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 作者： Vance
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    private static Log logger = LogFactory.getLog(DeviceServiceImpl.class);

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private MpProperty mpProperty;

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Device queryById(int id) {
        return deviceDao.queryById(id);
    }

    @Override
    public Device queryByMac(String mac) {
        return deviceDao.queryByMac(mac);
    }


    @Override
    public Device queryByNo(String deviceNumber) {
        return deviceDao.queryByNo(deviceNumber);
    }

    @Override
    public Device queryByQRTicket(String ticket) {
        return deviceDao.queryByQRTicket(ticket);
    }

    @Override
    public Device queryByDeviceId(String deviceId) {
        return deviceDao.queryByDeviceId(deviceId);
    }

    @Override
    public Page<Device> queryByCondition(String deviceIdOrMac, int pageNo, int pageSize) {
        Page<Device> page = new Page<>(pageNo, pageSize);
        page.setTotalCount(deviceDao.count(deviceIdOrMac));
        page.setResult(deviceDao.queryByCondition(deviceIdOrMac, page.getStart(), page.getPageSize()));
        return page;
    }

    @Override
    public void compelUnBind(String openId, String deviceId) {

        String json = "{\"device_id\": \"" + deviceId + "\", \"openid\": \"" + openId + "\"}";

        String accessToken = accessTokenService.getAccessToken();

        String url = mpProperty.getMpDeviceCompelUnBindUrl() + "?access_token=" + accessToken;
        Map result = MpApi.postJson(url, json, Map.class);
        LogFactory.getLog(this.getClass()).debug("解绑设备, result: " + result.toString() + ", openId: " + openId + ", deviceId: " + deviceId);
    }

    @Override
    public void compelBind(String openId, String deviceId) {
        String json = "{\"device_id\": \"" + deviceId + "\", \"openid\": \"" + openId + "\"}";

        String accessToken = accessTokenService.getAccessToken();

        String url = mpProperty.getMpDeviceCompelBindUrl() + "?access_token=" + accessToken;
        Map result = MpApi.postJson(url, json, Map.class);
        LogFactory.getLog(this.getClass()).debug("绑定设备, result: " + result.toString() + ", openId: " + openId + ", deviceId: " + deviceId);
    }

    @Override
    public Page<Device> list(String deviceIdOrMac, int pageNo, int pageSize) {
        return null;
    }

    @Override
    @Transactional
    public String add(Device device) {
        String accessToken = accessTokenService.getAccessToken();
        String productId = mpProperty.getProductId();
        device.setProductId(productId);
        if (StringUtils.isNotEmpty(productId)) {
            Map<String, String> params = Maps.newHashMap();
            params.put("access_token", accessToken);
            params.put("product_id", productId);
            Map<String, String> result = MpApi.get(mpProperty.getMpDeviceGetqrcodeUrl(), params, Map.class);
            String deviceId = result.get("deviceid");
            if (StringUtils.isEmpty(deviceId)) {
                throw new RuntimeException("system error");
            }
            device.setDeviceId(deviceId);
            device.setQrTicket(result.get("qrticket"));
            updateDeviceInfo(device);
        }
        deviceDao.add(device);
        return device.getQrTicket();
    }

    @Override
    @Transactional
    public void update(Device device) {
        updateDeviceInfo(device);
        deviceDao.update(device);
    }

    private void updateDeviceInfo(Device device) {
        MpDeviceInfo mpDeviceInfo = new MpDeviceInfo(device);
        String mpDeviceInfoJson = JsonUtils.toJson(mpDeviceInfo);
        String json = "{\"device_num\":\"1\",\"device_list\":[" + mpDeviceInfoJson + "],\"op_type\":\"1\"}";
        Map<String, String> result = MpApi.postJson(mpProperty.getMpDeviceAuthorizeDeviceUrl() + "?access_token=" + accessTokenService.getAccessToken(), json, Map.class);
        logger.debug("授权设备信息, result: " + result.toString() + ", info: " + json);
    }
}
