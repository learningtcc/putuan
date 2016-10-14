package com.advanpro.putuan.utils.cache;

/**
 * {说明}
 *
 * @author yechong
 * @since 2015/9/16 9:26
 */
public enum RedisLock {

    ACCESS_TOKEN;

    public enum Status {
        TRUE,   // 已获得锁
        FALSE;   // 未获得锁

        public static Status valueOf(int status) {
            switch (status) {
                case 1:
                    return TRUE;
            }
            return FALSE;
        }
    }
}