package com.advanpro.putuan.api.common;


import com.advanpro.putuan.model.User;

import java.io.Serializable;

/**
 * 作者： Joinly
 * 时间： 2015/11/27
 */
public class UserTokenWrapper implements Serializable {

    private static final long serialVersionUID = -7530183632044151063L;
    private static final long expireNumber = 10 * 60 * 1000;  // 过期时间10分钟

    private long userId;
    private long expireTime;

    /**
     * 缓存开始时间
     **/

    public int getUserId() {
        return (int) userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public UserTokenWrapper(User user, long expireTime) {
        this.userId = user.getId();
        this.expireTime = expireTime;
    }

    public void refresh() {
        this.expireTime = System.currentTimeMillis();
    }

    /**
     * 是否过期
     **/
    public boolean isExpire() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - expireTime) > expireNumber;
    }
}
