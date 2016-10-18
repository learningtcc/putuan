package com.advanpro.putuan.api.form;

import com.advanpro.putuan.model.User;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 作者： Vance
 * 时间： 2016/9/20
 * 描述： APP用户信息.
 */
public class UserVo {

    private int userId;

    private String account;

    private String phone;

    private String password;

    private String nickName;

    private int sex;

    private long birthday;

    private String province;

    private String city;

    private String headimgUrl;

    private String kneelCount;

    private String deviceId;

    private String verifyCode;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public String getKneelCount() {
        return kneelCount;
    }

    public void setKneelCount(String kneelCount) {
        this.kneelCount = kneelCount;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public User toUser() {
        User user = new User();
        user.setId(this.getUserId());
        user.setAccount(this.getAccount());
        user.setPhone(this.getPhone());
        user.setPassword(this.getPassword());
        user.setSex(this.getSex());
        user.setBirthday(new Date(this.getBirthday()));
        user.setNickName(this.getNickName());
        user.setProvince(this.getProvince());
        user.setCity(this.getCity());
        user.setHeadimgUrl(this.getHeadimgUrl());
        if (StringUtils.isNotEmpty(this.getKneelCount())) {
            user.setHistoried(1);
        }
        user.setUserType("APP");
        user.setStatus(1);
        return user;
    }

    public void populateUser(User user) {
        this.setUserId(user.getId());
        this.setAccount(user.getAccount());
        this.setBirthday(user.getBirthday().getTime());
        this.setHeadimgUrl(user.getHeadimgUrl());
        this.setNickName(user.getNickName());
        this.setPhone(user.getPhone());
        this.setSex(user.getSex());
        this.setProvince(user.getProvince());
        this.setCity(user.getCity());
    }
}
