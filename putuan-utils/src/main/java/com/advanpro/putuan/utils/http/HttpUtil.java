package com.advanpro.putuan.utils.http;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 作者： Vance
 * 时间： 2016/06/12
 * 描述： Http请求工具类
 */
public class HttpUtil {

    /**
     * 返回当前的 HttpServletRequest
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 返回当前的 HttpServletResponse
     *
     * @return response
     */
    public static HttpServletResponse getResponse() {
        ServletWebRequest servletWebRequest = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        if (servletWebRequest == null) {
            return null;
        }
        return servletWebRequest.getResponse();
    }

    /**
     * 返回当前的 HttpSession
     *
     * @return session
     */
    public static HttpSession getSession() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }
        return servletRequestAttributes.getRequest().getSession();
    }
}
