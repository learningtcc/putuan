package com.advanpro.putuan.web.common;


import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * 工程里的VM视图解析，可注入一些在页面上公共的类
 *
 * @author Retina.Ye
 */
public class WebVelocityViewResolver extends VelocityViewResolver {

    /** 在vm页面引用UrlBroker的key */
    private static final String URL_BROKER_KEY = "UrlBroker";

    private URLBrokerFactory urlBrokerBean;

    public URLBrokerFactory getUrlBrokerBean() {
        return urlBrokerBean;
    }

    public void setUrlBrokerBean(URLBrokerFactory urlBrokerBean) {
        this.urlBrokerBean = urlBrokerBean;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        VelocityView view = (VelocityView) super.buildView(viewName);
        view.addStaticAttribute(URL_BROKER_KEY, urlBrokerBean);
        view.addStaticAttribute("StringUtils", StringUtils.class);
        return view;
    }

}