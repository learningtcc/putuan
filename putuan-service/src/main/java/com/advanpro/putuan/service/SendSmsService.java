package com.advanpro.putuan.service;

/**
 * Created by liron on 2016/2/17.
 * 发送SMS短信服务
 */
public interface SendSmsService {
    boolean sendVerifyCode(String mobile, String verifyCode) throws Exception;
}
