package com.advanpro.putuan.model;

/**
 * 作者： Vance
 * 时间： 2016/7/15
 * 描述： ${todo}.
 */
public class Device extends BaseModel {

    private String deviceId;

    private String typeCode;

    private String deviceNumber;

    private String mac;

    private String connectProtocol;

    private String authKey;

    private String closeStrategy;

    private String connStrategy;

    private String cryptMethod;

    private String authVer;

    private String manuMacPos;

    private String serMacPos;

    private String bleSimpleProtocol;

    private String qrTicket;

    private String productId;

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

    public String getConnectProtocol() {
        return connectProtocol;
    }

    public void setConnectProtocol(String connectProtocol) {
        this.connectProtocol = connectProtocol;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getCloseStrategy() {
        return closeStrategy;
    }

    public void setCloseStrategy(String closeStrategy) {
        this.closeStrategy = closeStrategy;
    }

    public String getConnStrategy() {
        return connStrategy;
    }

    public void setConnStrategy(String connStrategy) {
        this.connStrategy = connStrategy;
    }

    public String getCryptMethod() {
        return cryptMethod;
    }

    public void setCryptMethod(String cryptMethod) {
        this.cryptMethod = cryptMethod;
    }

    public String getAuthVer() {
        return authVer;
    }

    public void setAuthVer(String authVer) {
        this.authVer = authVer;
    }

    public String getManuMacPos() {
        return manuMacPos;
    }

    public void setManuMacPos(String manuMacPos) {
        this.manuMacPos = manuMacPos;
    }

    public String getSerMacPos() {
        return serMacPos;
    }

    public void setSerMacPos(String serMacPos) {
        this.serMacPos = serMacPos;
    }

    public String getBleSimpleProtocol() {
        return bleSimpleProtocol;
    }

    public void setBleSimpleProtocol(String bleSimpleProtocol) {
        this.bleSimpleProtocol = bleSimpleProtocol;
    }

    public String getQrTicket() {
        return qrTicket;
    }

    public void setQrTicket(String qrTicket) {
        this.qrTicket = qrTicket;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public void defaultDevice() {
        this.setConnectProtocol("3");
        this.setAuthKey("");
        this.setCloseStrategy("1");
        this.setConnStrategy("4");
        this.setCryptMethod("0");
        this.setAuthVer("0");
        this.setManuMacPos("-1");
        this.setSerMacPos("-2");
    }
}
