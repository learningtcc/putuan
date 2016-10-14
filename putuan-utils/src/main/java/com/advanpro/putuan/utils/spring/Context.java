package com.advanpro.putuan.utils.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public abstract class Context {

    protected Log logger = LogFactory.getLog(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("init");
    }

    @PreDestroy
    public void destroy() {
        logger.info("destroy");
    }

}