package com.advanpro.putuan.utils.wx.resp;

/**
 * 回复消息的基类，封装了基本属性
 *
 * @author Retina.Ye
 */
public abstract class BaseMessage {

    /** 接收方帐号（收到的OpenID） */
    private String ToUserName;
    /** 开发者微信号 */
    private String FromUserName;
    /** 消息创建时间 （整型） */
    private String CreateTime;
    /** 消息类型 */
    private String MsgType;

    /**
     * 最终返回给微信的数据格式
     *
     * @return
     */
    public abstract String toMessage();

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
}
