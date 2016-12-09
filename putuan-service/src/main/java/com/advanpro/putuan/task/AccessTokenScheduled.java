package com.advanpro.putuan.task;

import com.advanpro.putuan.service.AccessTokenService;
import com.advanpro.putuan.utils.cache.CacheKey;
import com.advanpro.putuan.utils.cache.RedisCacheService;
import com.advanpro.putuan.utils.cache.RedisLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * {说明}
 *
 * @author yechong
 * @since 2015/9/16 9:39
 */
@Component
public class AccessTokenScheduled {

    private static Log logger = LogFactory.getLog(AccessTokenScheduled.class);

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private RedisCacheService cacheService;

    /**
     * 每60分执行
     */
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void action() {
        logger.debug("定时任务[获取access_token]开启");

        if (cacheService.setnx(CacheKey.accessTokenLock(), RedisLock.ACCESS_TOKEN) == RedisLock.Status.FALSE) {
            logger.debug("定时任务[获取access_token]已被其他应用执行，即将退出");
            return;
        }
        accessTokenService.updateAccessToken();
        cacheService.remove(CacheKey.accessTokenLock());
        logger.debug("定时任务[获取access_token]结束");
    }
}