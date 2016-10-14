package com.advanpro.putuan.utils.cache;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * redis特有接口
 *
 * @author Retina.Ye
 * @since 11/10/14 5:58 PM
 */
public interface RedisCacheService extends CacheService {

    Set<String> hkeys(String key);

    void hdel(String key, String field);

    void hdel(String key, String... field);

    void hSet(String key, String field, Object value, int second);

    long hincrBy(String key, String field, long num);

    long hincrBy(String key, String field, long num, int second);

    // set集合
    void sadd(String key, String value);

    void srem(String key, String value);

    long scard(String key);

    List<String> srandmember(String key, int count);

    boolean sismember(String key, Object value);

    RedisLock.Status setnx(String key, RedisLock redisLock);

    void zadd(String key, long score, String member);

    void zrem(String key, String member);

    long zcard(String key);

    long zscore(String key, String member);

    Set<String> zrange(String key, long start, long end);

    Jedis getJedis();

    /**
     * 分布式锁
     *
     * @param jedis
     * @param key
     * @return 成功获取锁
     */
    boolean lock(Jedis jedis, String key);

    boolean lock(Jedis jedis, String key, long timeout);

    boolean lock(Jedis jedis, String key, long timeout, int expiredTime);


    /**
     * 分布式解锁
     * 无论是否加锁成功，必须调用
     *
     * @param jedis
     * @param key
     */
    void unlock(Jedis jedis, String key);
}