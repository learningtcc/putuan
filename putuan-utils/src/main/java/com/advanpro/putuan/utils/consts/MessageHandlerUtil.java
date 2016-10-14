package com.advanpro.putuan.utils.consts;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 作者： Joinly
 * 时间： 2016/7/8
 * 描述： 微信消息解析帮助类.
 */
public class MessageHandlerUtil {

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request 封装了请求信息的HttpServletRequest对象
     * @return map 解析结果
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = Maps.newHashMap();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            System.out.println(e.getName() + "|" + e.getText());
            map.put(e.getName(), e.getText());
        }

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 处理消息Message
     *
     * @param map 封装了解析结果的Map
     * @return
     */
    public static String handleEventMessage(Map<String, String> map, String Content) {
        String responseMessage = StringUtils.EMPTY;
        String event = map.get("Event");
        if (MsgType.Event.SUBSCRIBE.equals(event)) {
            responseMessage = buildTextMessage(map, Content);
        }

        if (MsgType.Event.BIND.equals(event)) {
            responseMessage = buildTextMessage(map, Content);
        }

        if (MsgType.Event.UNBIND.equals(event)) {
            responseMessage = buildTextMessage(map, Content);
        }
        return responseMessage;
    }

    /**
     * 返回文本消息
     *
     * @param map 封装了解析结果的Map
     * @return
     */
    private static String buildTextMessage(Map<String, String> map, String content) {
        String responseMessageXml;
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        responseMessageXml = String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>",
                fromUserName, toUserName, System.currentTimeMillis(), content);
        return responseMessageXml;
    }

    /**
     * 构造图文消息
     *
     * @param map 封装了解析结果的Map
     * @return 图文消息XML字符串
     */
    public static String buildNewsMessage(Map<String, String> map, NewsItem item) {
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        String itemContent = buildSingleItem(item);

        String content = String.format("<xml>\n" +
                "<ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "<FromUserName><![CDATA[%s]]></FromUserName>\n" +
                "<CreateTime>%s</CreateTime>\n" +
                "<MsgType><![CDATA[news]]></MsgType>\n" +
                "<ArticleCount>%s</ArticleCount>\n" +
                "<Articles>\n" + "%s" +
                "</Articles>\n" +
                "</xml> ", fromUserName, toUserName, System.currentTimeMillis(), 1, itemContent);
        return content;
    }

    /**
     * 绑定设备消息
     *
     * @param map 封装了解析结果的Map
     * @return
     */
    private static String buildBindMessage(Map<String, String> map, String Content) {
        String responseMessageXml;
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        String deviceId = map.get("DeviceID");
        responseMessageXml = String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[device_event]]></MsgType>" +
                        "<Event><![CDATA[bind]]></Event>" +
                        "<DeviceType><![CDATA[%s]]></DeviceType>" +
                        "<DeviceID><![CDATA[%s]]></DeviceID>" +
                        "<SessionID>%s</SessionID>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>",
                fromUserName, toUserName, System.currentTimeMillis(), map.get("DeviceType"), deviceId, map.get("SessionID"), Content);
        return responseMessageXml;
    }

    /**
     * 解绑设备消息
     *
     * @param map 封装了解析结果的Map
     * @return
     */
    private static String buildUnBindMessage(Map<String, String> map, String Content) {
        String responseMessageXml;
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        String deviceId = map.get("DeviceID");
        responseMessageXml = String.format(
                "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[device_event]]></MsgType>" +
                        "<Event><![CDATA[bind]]></Event>" +
                        "<DeviceType><![CDATA[%s]]></DeviceType>" +
                        "<DeviceID><![CDATA[%s]]></DeviceID>" +
                        "<SessionID>%s</SessionID>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>",
                fromUserName, toUserName, System.currentTimeMillis(), map.get("DeviceType"), deviceId, map.get("SessionID"), Content);
        return responseMessageXml;
    }

    /**
     * 生成图文消息的一条记录
     *
     * @param item
     * @return
     */
    private static String buildSingleItem(NewsItem item) {
        String title = item.getTitle();
        String desc = item.getDescription();
        String picUrl = item.getPicUrl();
        String xmlTitle = "%s";
        String xmlDesc = "%s";
        String xmlPicUrl = "%s";
        if (StringUtils.isNotEmpty(title)) {
            xmlTitle = "<Title><![CDATA[%s]]></Title>";
        }
        if (StringUtils.isNotEmpty(desc)) {
            xmlDesc = "<Description><![CDATA[%s]]></Description>";
        }
        if (StringUtils.isNotEmpty(picUrl)) {
            xmlPicUrl = "<PicUrl><![CDATA[%s]]></PicUrl>";
        }
        String itemContent = String.format("<item>" +
                xmlTitle +
                xmlDesc +
                xmlPicUrl +
                "<Url><![CDATA[%s]]></Url>" +
                "</item>", item.Title, item.Description, picUrl, item.Url);
        return itemContent;
    }
}
