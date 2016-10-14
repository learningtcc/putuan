package com.advanpro.putuan.web.common;

import com.advanpro.putuan.model.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者： Joinly
 * 时间： 2016/1/20
 * 描述： ${todo}.
 */
public abstract class BaseController {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected static final String ERROR_404_FORWARD = "error/common404Page";
    protected static final String ERROR_500_FORWARD = "error/common500Page";
    private static final String LOGIN_USER = "LoginUser";


    public void doLogin(HttpServletRequest request, Account account) {
        request.getSession().setAttribute(LOGIN_USER, account);
    }

    public Account getLoginUser(HttpServletRequest request) {
        return (Account) request.getSession().getAttribute(LOGIN_USER);
    }

    public void doLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGIN_USER);
    }
}
