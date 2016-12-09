package com.advanpro.putuan.utils.cache;

/**
 * 作者： Joinly
 * 时间： 2016/3/14
 * 描述： 缓存的KEY，必须从此获取
 * 一目了然哪些自定义的数据加入了cache
 */
public class CacheKey {

    private static final String PREFIX_NORMAL_WX = "NORMAL_WX_";    // 微信端普通缓存

    private static final String PREFIX_WEBPAGE_WX = "WEBPAGE_WX_";   //微信页面缓存

    private static final String PREFIX_NORMAL_PHONE_VERIFYCODE = "NORMAL_PHONE_VERIFYCODE_";

    public static final String PREFIX_NORMAL_USER_TOKEN = "NORMAL_USER_TOKEN";

    public static final String PREFIX_NORMAL_ACCOUNT_TOKEN = "NORMAL_ACCOUNT_TOKEN";

    public static String accessToken() {
        return PREFIX_NORMAL_WX + "accessToken";
    }

    public static String accessTokenLock() {
        return PREFIX_NORMAL_WX + "accessTokenLock";
    }

    //网页授权accesstoken
    public static String getWebPageAccessToken() {
        return PREFIX_WEBPAGE_WX + "accessToken";
    }

    public static String accessTokenLockByWebPage() {
        return PREFIX_WEBPAGE_WX + "accessTokenLock";
    }

    public static String phoneVerifyCode(String suffix) {
        return PREFIX_NORMAL_PHONE_VERIFYCODE + suffix;
    }


}
