package com.advanpro.putuan.utils.cache;


import com.advanpro.putuan.utils.spring.ApplicationContextUtils;

/**
 * {说明}
 *
 * @author yechong
 * @since 2015/7/13 15:06
 */
public class CacheFactory {

    public static RedisCacheService getNormalCacheService() {
        return (RedisCacheService) ApplicationContextUtils.getBean("cacheService");
    }

    public static RedisCacheService getDbCacheService() {
        return (RedisCacheService) ApplicationContextUtils.getBean("cacheDbService");
    }

    public static RedisCacheService getSessionCacheService() {
        return (RedisCacheService) ApplicationContextUtils.getBean("cacheSessionService");
    }
}