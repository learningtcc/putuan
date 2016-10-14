package com.advanpro.putuan.model;

/**
 * {说明}
 *
 * @author Retina.Ye
 */
public class MpAccessToken extends Result {

    private static final long serialVersionUID = -4648893212772912855L;
    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}