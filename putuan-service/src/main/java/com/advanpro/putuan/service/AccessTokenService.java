package com.advanpro.putuan.service;

/**
 * {说明}
 *
 * @author yechong
 * @since 2015/9/10 9:17
 */
public interface AccessTokenService {

    String getAccessToken();

    void updateAccessToken();

    String getWebPageAccessToken(String openId);
}