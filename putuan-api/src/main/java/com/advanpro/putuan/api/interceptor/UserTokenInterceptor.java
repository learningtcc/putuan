package com.advanpro.putuan.api.interceptor;

import com.advanpro.putuan.api.common.UserTokenWrapper;
import com.advanpro.putuan.utils.cache.CacheKey;
import com.advanpro.putuan.utils.cache.RedisCacheService;
import com.advanpro.putuan.utils.json.JsonResult;
import com.advanpro.putuan.utils.json.StatusCode;
import com.advanpro.putuan.utils.token.TokenProcessor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 作者： Joinly
 * 时间： 2015/11/5
 * 描述： 用户Token拦截器.
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    //private static final Log logger = LogFactory.getLog(UserTokenInterceptor.class);

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private TokenProcessor tokenProcessor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = request.getHeader("token");
        String tokenV = request.getHeader("tokenV");
        // 必须带token与tokenV
        if (StringUtils.isBlank(token) || StringUtils.isBlank(tokenV)) {
            response.setContentType("text/json; charset=UTF-8");
            response.getWriter().write(new JsonResult(StatusCode.TOKEN_IS_EMPTY).toJson());
            return false;
        }
        try {
            // 验证token是否存在
            UserTokenWrapper userTokenWrapper = (UserTokenWrapper) redisCacheService.hGet(CacheKey.PREFIX_NORMAL_USER_TOKEN, token);
            if ((null == userTokenWrapper) || userTokenWrapper.isExpire()) {  // redis中不存在或者已过期
                redisCacheService.hdel(CacheKey.PREFIX_NORMAL_USER_TOKEN, token);
                response.setContentType("text/json; charset=UTF-8");
                response.getWriter().write(new JsonResult(StatusCode.TOKEN_IS_EXPIRE).toJson());
                return false;
            }
            if (!tokenProcessor.isCorrect(token, tokenV)) {  // token错误
                response.setContentType("text/json; charset=UTF-8");
                response.getWriter().write(new JsonResult(StatusCode.TOKEN_IS_INVALID).toJson());
                return false;
            }

            userTokenWrapper.refresh();
            redisCacheService.hSet(CacheKey.PREFIX_NORMAL_USER_TOKEN, token, userTokenWrapper);  // 刷新token时间
            //logger.info(userTokenWrapper.getUserId() + "@" + ServerUtil.getClientIp(request) + "/" + request.getRequestURI());
        } catch (Exception e) {
            LogFactory.getLog(this.getClass()).error("验证token出错", e);
            response.setContentType("text/json; charset=UTF-8");
            response.getWriter().write(new JsonResult(StatusCode.INTERNAL_ERROR).toJson());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
