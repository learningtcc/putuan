package com.advanpro.putuan.web.filter;

import com.advanpro.putuan.model.Account;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 作者： Joinly
 * 时间： 2016/7/18
 * 描述： 系统安全过滤器.
 */
public class SystemSecurityFilter implements Filter {

    private Set<String> ignorePaths = new HashSet<>();

    private String passPaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        passPaths = filterConfig.getInitParameter("passPaths");
        StringTokenizer tokenizer = new StringTokenizer(passPaths, ",");
        while (tokenizer.hasMoreTokens()) {
            ignorePaths.add(tokenizer.nextToken().trim());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        String uri = request.getRequestURI();

        if (letGo(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            //处理管理后台登陆
            Account account = (Account) request.getSession().getAttribute("LoginUser");
            if (account == null) {
                String requestType = request.getHeader("x-requested-with");
                if (!StringUtils.isEmpty(requestType) && requestType.equalsIgnoreCase("XMLHttpRequest")) {
                    response.setHeader("sessionstatus", "timeout");
                    response.sendError(401, "session timeout.");
                } else {
                    response.sendRedirect("/login");
                }
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
    }

    private boolean letGo(String uri) {
        for (String path : ignorePaths) {
            if (uri.startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
