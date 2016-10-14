package com.advanpro.putuan.model;

/**
 * 作者： Joinly
 * 时间： 2016/7/21
 * 描述： ${todo}.
 */
public class Account extends BaseModel {

    private String userName;

    private String password;

    private String nickName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
