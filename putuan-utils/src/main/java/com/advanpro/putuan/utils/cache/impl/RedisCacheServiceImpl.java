package com.advanpro.putuan.utils.cache.impl;

import com.advanpro.putuan.utils.cache.RedisCacheService;
import com.advanpro.putuan.utils.cache.RedisLock;
import com.advanpro.putuan.utils.cache.SerializeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Redis缓存实现
 *
 * @author Retina.Ye
 */
public class RedisCacheServiceImpl implements RedisCacheService {

    protected Log logger = LogFactory.getLog(this.getClass());

    private String cacheAddress = "127.0.0.1:6379";

    public String getCacheAddress() {
        return cacheAddress;
    }

    public void setCacheAddress(String cacheAddress) {
        this.cacheAddress = cacheAddress;
    }

    private JedisPool pool = null;


    public RedisCacheServiceImpl(String address) {
        this.cacheAddress = address;

        logger.info("cacheAddress:" + cacheAddress);
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            /* config.setMaxActive(-1);*/
            /* config.setMaxIdle();*/
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(200);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 30);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);

            String[] addr = cacheAddress.split(":");
            pool = new JedisPool(config, addr[0], Integer.parseInt(addr[1]), 1000 * 30);
            logger.info("pool:" + pool);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("不能初始化Redis客户端", e);
        }

    }

    @Override
    public Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            byte[] data = jedis.get(key.getBytes());
            if (data == null || data.length <= 0) {
                return null;
            }
            return SerializeUtil.unSerialize(data);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void put(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void put(String key, Object value, int second) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            jedis.expire(key.getBytes(), second);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void hSet(String key, String field, Object value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public Object hGet(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            byte[] data = jedis.hget(key.getBytes(), field.getBytes());
            if (data == null || data.length <= 0) {
                return null;
            }
            return SerializeUtil.unSerialize(data);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void remove(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key.getBytes());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void removeAll() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.flushAll();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hkeys(key);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void hdel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hdel(key, field);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void hdel(String key, String... field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hdel(key, field);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void hSet(String key, String field, Object value, int second) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
            jedis.expire(key.getBytes(), second);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public long hincrBy(String key, String field, long num) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hincrBy(key.getBytes(), field.getBytes(), num);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public long hincrBy(String key, String field, long num, int second) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            long result = jedis.hincrBy(key.getBytes(), field.getBytes(), num);
            jedis.expire(key.getBytes(), second);
            return result;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.srem(key, value);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public List<String> srandmember(String key, int count) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srandmember(key, count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public boolean sismember(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key.getBytes(), SerializeUtil.serialize(value));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    public RedisLock.Status setnx(String key, RedisLock redisLock) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long status = jedis.setnx(key.getBytes(), SerializeUtil.serialize(redisLock.name()));
            return RedisLock.Status.valueOf(status.intValue());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void zadd(String key, long score, String member) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.zadd(key, score, member);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public void zrem(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.zrem(key, member);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public long zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Double score = jedis.zscore(key, member);
            return score == null ? -1 : Math.round(score);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrange(key, start, end);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            pool.returnResourceObject(jedis);
            throw new RuntimeException("Redis出现错误！" + e.getMessage(), e);
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public Jedis getJedis() {
        return pool.getResource();
    }

    //加锁标志
    private static final String LOCKED = "TRUE";
    private static final long ONE_MILLI_NANOS = 1000000L;
    //默认超时时间（毫秒）
    private static final long DEFAULT_TIME_OUT = 3000;
    private static final Random r = new Random();
    //锁的超时时间（秒），过期删除
    private static final int EXPIRE = 5 * 60;
    //锁状态标志
    private boolean locked = false;

    @Override
    public boolean lock(Jedis jedis, String key) {
        return lock(jedis, key, DEFAULT_TIME_OUT);
    }

    @Override
    public void unlock(Jedis jedis, String key) {
        try {
            if (locked) {
                jedis.del(key);
            }
        }
        finally {
            pool.returnResourceObject(jedis);
        }
    }

    @Override
    public boolean lock(Jedis jedis, String key, long timeout) {
        return lock(jedis, key, timeout, EXPIRE);
    }

    @Override
    public boolean lock(Jedis jedis, String key, long timeout, int expiredTime) {
        long nanoTime = System.nanoTime();
        timeout *= ONE_MILLI_NANOS;
        try {
            while ((System.nanoTime() - nanoTime) < timeout) {
                if (jedis.setnx(key, LOCKED) == 1) {
                    logger.debug("成功获得锁.key:" + key);
                    jedis.expire(key, expiredTime);
                    locked = true;
                    return true;
                }
                // 短暂休眠，nanoTime避免出现活锁
                Thread.sleep(3, r.nextInt(500));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}