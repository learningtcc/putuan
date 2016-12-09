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

    private String deviceNumber;

    private String mac;

    private String typeCode;

    private String deviceType;

    private long time;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void populateDevice(Device device) {
        if (device != null) {
            this.setDeviceId(device.getDeviceId());
            this.setDeviceNumber(device.getDeviceNumber());
            this.setMac(device.getMac());
            this.setTypeCode(device.getTypeCode());
            String deviceType = DeviceType.valueOf(device.getTypeCode()).desc();
            this.setDeviceType(deviceType);
            this.setTime(device.getCreateTime().getTime());
        }
    }
}
