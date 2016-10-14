package com.advanpro.putuan.web.form;

import com.advanpro.putuan.model.User;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 作者： Vance
 * 时间： 2016/9/29
 * 描述： 微信用户信息.
 */
public class UserVo {

    private int userId;

    private String openId;

    private String phone;

    private String nickName;

    private int sex;

    private int age;

    private Date birthday;

    private String province;

    private String city;

    private String headimgUrl;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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


    public void populateUser(User user) {
        this.setUserId(user.getId());
        this.setOpenId(user.getOpenId());
        this.setAge(user.getAge());
        this.setBirthday(user.getBirthday());
        this.setHeadimgUrl(user.getHeadimgUrl());
        this.setNickName(user.getNickName());
        this.setPhone(user.getPhone());
        this.setSex(user.getSex());
        this.setProvince(user.getProvince());
        this.setCity(user.getCity());
    }
}
