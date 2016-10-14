package com.advanpro.putuan.api.controller;

import com.advanpro.putuan.api.common.UserTokenWrapper;
import com.advanpro.putuan.model.User;
import com.advanpro.putuan.service.UserService;
import com.advanpro.putuan.utils.cache.CacheKey;
import com.advanpro.putuan.utils.cache.RedisCacheService;
import com.advanpro.putuan.utils.json.StatusCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liron on 2016/8/30.
 */
@Controller
public class BaseController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    protected UserService userService;
    @Autowired
    protected RedisCacheService cacheService;

    /**
     * 根据SessionToken获取当前用户信息
     */
    protected User getCurrentUser(HttpServletRequest request) {
        User user = null;
        String token = request.getHeader("token");
        if (token != null && token.length() > 0) {
            UserTokenWrapper userTokenWrapper = (UserTokenWrapper) cacheService.hGet(CacheKey.PREFIX_NORMAL_USER_TOKEN, token);
            if (!userTokenWrapper.isExpire()) {
                int userId = userTokenWrapper.getUserId();
                user = userService.get(userId);
            }
        }
        return user;
    }

    /**
     * 检查验证码
     */
    protected StatusCode checkVerifyCode(String account, String verifyCode, boolean remove) {
        if (verifyCode == null || verifyCode.isEmpty())
            return StatusCode.INVALID_PARAMETER;
        String cacheKey = CacheKey.phoneVerifyCode(account);
        String randomCode = (String) cacheService.get(cacheKey);  // 从缓存查询验证码
        if (null == randomCode)
            return StatusCode.VERIFY_CODE_IS_EXPIRE;
        if (!randomCode.equals(verifyCode))
            return StatusCode.VERIFY_CODE_ERROR;
        if (remove)
            cacheService.remove(cacheKey); // 将验证码设为无效
        return StatusCode.OK;
    }
}

