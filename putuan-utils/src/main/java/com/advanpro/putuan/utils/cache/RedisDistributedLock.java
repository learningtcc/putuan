package com.advanpro.putuan.utils.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redis分布式锁
 *
 * @author Retina.Ye
 * @since 11/22/14 10:16 PM
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisDistributedLock {
    String key();  // redis的key

    /**
     * 最长等待时间，单位秒
     * 超过等待时间，即获取不到锁
     *
     * @return
     */
    int maxWait() default 3;

    /**
     * key的过期时间，单位秒
     * 每个线程执行最大时间，redis的key将会过期，其他线程将会获得锁
     *
     * @return
     */
    int expiredTime() default 60;
}