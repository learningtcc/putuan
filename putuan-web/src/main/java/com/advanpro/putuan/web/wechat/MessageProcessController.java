package com.advanpro.putuan.web.wechat;

import com.advanpro.putuan.utils.consts.MessageHandlerUtil;
import com.advanpro.putuan.utils.consts.MsgType;
import com.advanpro.putuan.utils.wx.MpProperty;
import com.advanpro.putuan.web.common.BaseController;
import com.advanpro.putuan.web.wechat.helper.ClickMessageProcessHelper;
import com.advanpro.putuan.web.wechat.helper.MessageProcessHelper;
import com.advanpro.putuan.web.wechat.helper.VerifyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 作者： Joinly
 * 时间： 2016/7/7
 * 描述： 微信回调请求处理.
 */
@Controller
public class MessageProcessController extends BaseController {

    @Autowired
    private MessageProcessHelper messageProcessHelper;

    @Autowired
    private ClickMessageProcessHelper clickMessageProcessHelper;

    @Autowired
    private MpProperty mpProperty;

    /**
     * 开发者接入验证
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/wx/callback", method = RequestMethod.GET)
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String signature = request.getParameter("signature");
            String echostr = request.getParameter("echostr");

            if (VerifyUtils.checkSignature(signature, timestamp, nonce, mpProperty.getToken())) {
                out(echostr, response);
            }

        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("开发者接入验证出错：", e);
            out("", response);
        }
    }

    /**
     * 消息处理中心
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/wx/callback", method = RequestMethod.POST)
    public void messageHandle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> map = MessageHandlerUtil.parseXml(request);
            String backMsg = "";

            //业务处理
            String msgType = map.get("MsgType");
            if (MsgType.TEXT.equals(msgType)) {
                System.out.println("=====文本消息=====");
            }

            String event = map.get("Event");
            // 基础事件推送
            if (MsgType.EVENT.equals(msgType)) {
                if (MsgType.Event.SUBSCRIBE.equals(event)) {
                    backMsg = messageProcessHelper.processSubscribe(map);
                }
                if (MsgType.Event.UNSUBSCRIBE.equals(event)) {
                    backMsg = messageProcessHelper.processUnSubscribe(map);
                }
                // 菜单点击事件
                if (MsgType.Event.CLICK.equals(event)) {
                    backMsg = clickMessageProcessHelper.processMessage(map);
                }
                if (MsgType.Event.SCAN.equals(event)) {
                    backMsg = messageProcessHelper.processScan(map);
                }
            }
            //设备事件
            if (MsgType.DEVICE_EVENT.equals(msgType) || MsgType.DEVICE_TEXT.equals(msgType)) {
                if (StringUtils.isNotEmpty(event)) {
                    if (MsgType.Event.BIND.equals(event)) {
                        backMsg = messageProcessHelper.processBind(map);
                    }

                    if (MsgType.Event.UNBIND.equals(event)) {
                        backMsg = messageProcessHelper.processUnBind(map);
                    }
                } else {
                        messageProcessHelper.processDeviceMessage(map);
                }
            }

            //返回信息
            out(backMsg, response);
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("消息处理出错：", e);
            out("", response);
        }
    }

    /**
     * 输出字符串
     */
    protected void out(String str, HttpServletResponse response) {
        Writer out = null;
        try {
            response.setContentType("text/xml;charset=UTF-8");
            out = response.getWriter();
            out.append(str);
            out.flush();
        } catch (IOException e) {
            // ignore
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

}
