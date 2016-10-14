package com.advanpro.putuan.model;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * 作者： Joinly
 * 时间： 2016/7/7
 * 描述： 用户信息.
 */
public class User extends BaseModel {

    private String openId;

    private String account;

    private String nickName;

    private String phone;

    private String password;

    private int sex;

    private int age;

    private Date birthday;

    private String language;

    private String city;

    private String province;

    private String country;

    private String headimgUrl;

    private String remark;

    private int historied;

    private int userId;

    private String userType;

    private int status;

    private String bindNickName;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHistoried() {
        return historied;
    }

    public void setHistoried(int historied) {
        this.historied = historied;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBindNickName() {
        return bindNickName;
    }

    public void setBindNickName(String bindNickName) {
        this.bindNickName = bindNickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static User parse(Map map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        User user = new User();
        user.setOpenId(StringUtils.defaultString(String.valueOf(map.get("openid"))));
        user.setNickName(StringUtils.defaultString(String.valueOf(map.get("nickname"))));
        user.setSex((int) map.get("sex"));
        user.setLanguage(StringUtils.defaultString(String.valueOf(map.get("language"))));
        user.setCity(StringUtils.defaultString(String.valueOf(map.get("city"))));
        user.setProvince(StringUtils.defaultString(String.valueOf(map.get("province"))));
        user.setCountry(StringUtils.defaultString(String.valueOf(map.get("country"))));
        user.setHeadimgUrl(StringUtils.defaultString(String.valueOf(map.get("headimgurl"))));
        user.setRemark(StringUtils.defaultString(String.valueOf(map.get("remark"))));
        return user;
    }
}
