package com.advanpro.putuan.utils.cache;

/**
 * 缓存服务，用于缓存页面内容的以及页面对象
 *
 * @author Retina.Ye
 */
public interface CacheService {

    Object get(String key);

    void put(String key, Object value);

    void put(String key, Object value, int second);

    void hSet(String key, String field, Object value);

    Object hGet(String key, String field);

    void remove(String key);

    void removeAll();
}