package com.advanpro.putuan.api.form;

import com.advanpro.putuan.model.Device;
import com.advanpro.putuan.model.type.DeviceType;

import static com.advanpro.putuan.model.type.DeviceType.*;

/**
 * 作者： Vance
 * 时间： 2016/9/27
 * 描述： ${todo}.
 */
public class DeviceVo {

    private String deviceId;

    private String mac;

    private String deviceType;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void populateDevice(Device device) {
        this.setDeviceId(device.getDeviceId());
        this.setMac(device.getMac());
        String deviceType = DeviceType.valueOf(device.getTypeCode()).desc();
        this.setDeviceType(deviceType);
    }
}
