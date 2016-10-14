package com.advanpro.putuan.model;

import org.apache.commons.lang.math.NumberUtils;

import java.io.Serializable;

/**
 * 需要接收微信返回数据的对象需要继承此对象
 *
 * @author Retina.Ye
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 7214400972490391606L;
    private String errcode;
    private String errmsg;

    public boolean success() {
        if (NumberUtils.toInt(errcode) == 0) {
            return true;
        }
        return false;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
