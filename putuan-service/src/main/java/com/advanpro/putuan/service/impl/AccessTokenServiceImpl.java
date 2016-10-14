package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.model.MpAccessToken;
import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.utils.cache.CacheKey;
import com.advanpro.putuan.utils.cache.RedisCacheService;
import com.advanpro.putuan.utils.wx.MpApi;
import com.advanpro.putuan.utils.wx.MpProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * {说明}
 *
 * @author yechong
 * @since 2015/9/10 9:17
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private MpProperty mpProperty;

    @Autowired
    private RedisCacheService cacheService;

    @Override
    public String getAccessToken() {
        String accessToken = (String) cacheService.get(CacheKey.accessToken());
        if (StringUtils.isEmpty(accessToken)) {
            updateAccessToken();
        }
        return (String) cacheService.get(CacheKey.accessToken());
    }

    @Override
    public void updateAccessToken() {
        Map<String, String> params = new HashMap<>(3);
        params.put("grant_type", "client_credential");
        params.put("appid", mpProperty.getAppId());
        params.put("secret", mpProperty.getAppSecret());
        MpAccessToken mpAccessToken = MpApi.get(mpProperty.getMpTokenUrl(), params, MpAccessToken.class);
        //logger.info("获取access_token，返回数据：" + Json.toJson(mpAccessToken));
        cacheService.put(CacheKey.accessToken(), mpAccessToken.getAccess_token(), mpAccessToken.getExpires_in() - (60 * 5)); // 提前5分钟过期
    }

    @Override
    public String getWebPageAccessToken(String openId) {
        return (String) cacheService.get(CacheKey.getWebPageAccessToken());
    }
}