package com.advanpro.putuan.utils.wx;

/**
 * {说明}
 *
 * @author yechong
 * @since 2015/9/9 17:31
 */
public class MpProperty {

    private String appId;
    private String appSecret;
    private String token;
    private String originId;
    private String productId;

    private String uploadPath;
    private String domainUpload;

    private String mpTokenUrl;
    private String mpShorturlUrl;
    private String mpTicketGetTicketUrl;
    private String mpMessageCustomSendUrl;
    private String mpMenuCreateUrl;
    private String mpQrcodeCreateUrl;
    private String mpUserInfoUrl;
    private String mpOauth2AuthorizeUrl;
    private String mpOauth2AccessTokenUrl;
    private String mpSnsUserinfoUrl;

    private String mpDeviceGetqrcodeUrl;
    private String mpDeviceAuthorizeDeviceUrl;
    private String mpDeviceCreateQrcodeUrl;
    private String mpDeviceGetStatUrl;
    private String mpDeviceCompelBindUrl;
    private String mpDeviceCompelUnBindUrl;
    private String mpDeviceTransMsgUrl;

    private String mpUploadImageUrl;

    private String mpUploadMediaUrl;

    //网页授权获取的accesstoken的url
    private String webAccessTokenUrl;

    //刷新网页授权获取的accesstoken的url
    private String webRefreshTokenUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getDomainUpload() {
        return domainUpload;
    }

    public void setDomainUpload(String domainUpload) {
        this.domainUpload = domainUpload;
    }

    public String getMpTokenUrl() {
        return mpTokenUrl;
    }

    public void setMpTokenUrl(String mpTokenUrl) {
        this.mpTokenUrl = mpTokenUrl;
    }

    public String getMpTicketGetTicketUrl() {
        return mpTicketGetTicketUrl;
    }

    public void setMpTicketGetTicketUrl(String mpTicketGetTicketUrl) {
        this.mpTicketGetTicketUrl = mpTicketGetTicketUrl;
    }

    public String getMpMessageCustomSendUrl() {
        return mpMessageCustomSendUrl;
    }

    public void setMpMessageCustomSendUrl(String mpMessageCustomSendUrl) {
        this.mpMessageCustomSendUrl = mpMessageCustomSendUrl;
    }

    public String getMpSnsUserinfoUrl() {
        return mpSnsUserinfoUrl;
    }

    public void setMpSnsUserinfoUrl(String mpSnsUserinfoUrl) {
        this.mpSnsUserinfoUrl = mpSnsUserinfoUrl;
    }

    public String getMpMenuCreateUrl() {
        return mpMenuCreateUrl;
    }

    public void setMpMenuCreateUrl(String mpMenuCreateUrl) {
        this.mpMenuCreateUrl = mpMenuCreateUrl;
    }

    public String getMpQrcodeCreateUrl() {
        return mpQrcodeCreateUrl;
    }

    public void setMpQrcodeCreateUrl(String mpQrcodeCreateUrl) {
        this.mpQrcodeCreateUrl = mpQrcodeCreateUrl;
    }

    public String getMpDeviceGetqrcodeUrl() {
        return mpDeviceGetqrcodeUrl;
    }

    public void setMpDeviceGetqrcodeUrl(String mpDeviceGetqrcodeUrl) {
        this.mpDeviceGetqrcodeUrl = mpDeviceGetqrcodeUrl;
    }

    public String getMpOauth2AuthorizeUrl() {
        return mpOauth2AuthorizeUrl;
    }

    public void setMpOauth2AuthorizeUrl(String mpOauth2AuthorizeUrl) {
        this.mpOauth2AuthorizeUrl = mpOauth2AuthorizeUrl;
    }

    public String getMpOauth2AccessTokenUrl() {
        return mpOauth2AccessTokenUrl;
    }

    public void setMpOauth2AccessTokenUrl(String mpOauth2AccessTokenUrl) {
        this.mpOauth2AccessTokenUrl = mpOauth2AccessTokenUrl;
    }

    public String getMpShorturlUrl() {
        return mpShorturlUrl;
    }

    public void setMpShorturlUrl(String mpShorturlUrl) {
        this.mpShorturlUrl = mpShorturlUrl;
    }

    public String getMpDeviceAuthorizeDeviceUrl() {
        return mpDeviceAuthorizeDeviceUrl;
    }

    public void setMpDeviceAuthorizeDeviceUrl(String mpDeviceAuthorizeDeviceUrl) {
        this.mpDeviceAuthorizeDeviceUrl = mpDeviceAuthorizeDeviceUrl;
    }

    public String getMpDeviceCreateQrcodeUrl() {
        return mpDeviceCreateQrcodeUrl;
    }

    public void setMpDeviceCreateQrcodeUrl(String mpDeviceCreateQrcodeUrl) {
        this.mpDeviceCreateQrcodeUrl = mpDeviceCreateQrcodeUrl;
    }

    public String getMpDeviceGetStatUrl() {
        return mpDeviceGetStatUrl;
    }

    public void setMpDeviceGetStatUrl(String mpDeviceGetStatUrl) {
        this.mpDeviceGetStatUrl = mpDeviceGetStatUrl;
    }

    public String getMpDeviceCompelBindUrl() {
        return mpDeviceCompelBindUrl;
    }

    public void setMpDeviceCompelBindUrl(String mpDeviceCompelBindUrl) {
        this.mpDeviceCompelBindUrl = mpDeviceCompelBindUrl;
    }

    public String getMpDeviceCompelUnBindUrl() {
        return mpDeviceCompelUnBindUrl;
    }

    public void setMpDeviceCompelUnBindUrl(String mpDeviceCompelUnBindUrl) {
        this.mpDeviceCompelUnBindUrl = mpDeviceCompelUnBindUrl;
    }

    public String getMpDeviceTransMsgUrl() {
        return mpDeviceTransMsgUrl;
    }

    public void setMpDeviceTransMsgUrl(String mpDeviceTransMsgUrl) {
        this.mpDeviceTransMsgUrl = mpDeviceTransMsgUrl;
    }

    public String getMpUserInfoUrl() {
        return mpUserInfoUrl;
    }

    public void setMpUserInfoUrl(String mpUserInfoUrl) {
        this.mpUserInfoUrl = mpUserInfoUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMpUploadImageUrl() {
        return mpUploadImageUrl;
    }

    public void setMpUploadImageUrl(String mpUploadImageUrl) {
        this.mpUploadImageUrl = mpUploadImageUrl;
    }

    public String getMpUploadMediaUrl() {
        return mpUploadMediaUrl;
    }

    public void setMpUploadMediaUrl(String mpUploadMediaUrl) {
        this.mpUploadMediaUrl = mpUploadMediaUrl;
    }

    public String getWebAccessTokenUrl() {
        return webAccessTokenUrl;
    }

    public void setWebAccessTokenUrl(String webAccessTokenUrl) {
        this.webAccessTokenUrl = webAccessTokenUrl;
    }

    public String getWebRefreshTokenUrl() {
        return webRefreshTokenUrl;
    }

    public void setWebRefreshTokenUrl(String webRefreshTokenUrl) {
        this.webRefreshTokenUrl = webRefreshTokenUrl;
    }
}