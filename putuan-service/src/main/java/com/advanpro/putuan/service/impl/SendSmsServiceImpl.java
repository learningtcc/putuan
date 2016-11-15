package com.advanpro.putuan.service.impl;

import com.advanpro.putuan.service.SendSmsService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by liron on 2016/2/17.
 */
@Service
public class SendSmsServiceImpl implements SendSmsService {
    @Override
    public boolean sendVerifyCode(String mobile, String verifyCode) throws Exception {
        boolean ret = false;
        String requestText = "apikey=6696ba24a2675abef6d15305ad80237d";
        requestText += "&mobile=" + URLEncoder.encode(mobile, "UTF-8");
        requestText += "&text=" + URLEncoder.encode("【大白牛】您的验证码是" + verifyCode, "UTF-8");

        URL url = new URL("https://sms.yunpian.com/v1/sms/send.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json;charset=utf-8;");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8;");
        conn.setRequestProperty("Cache-Control", "no-cache");
        OutputStream os = conn.getOutputStream();
        os.write(requestText.getBytes());
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            for (int ch; (ch = in.read()) != -1; ) {
                byteStream.write(ch);
            }
            String content = byteStream.toString("UTF-8");
            JSONObject resp = new JSONObject(content);
            if (resp.has("result")) {
                JSONObject result = resp.getJSONObject("result");
                if (result.getInt("count") > 0)
                    ret = true;
            } else if (resp.has("msg")) {
                // 调用失败
                throw new Exception(resp.getString("msg"));
            } else if (resp.has("code")) {
                switch (resp.getInt("code")) {
                    case 8:
                        throw new Exception("同一手机号30秒内重复提交相同的内容");
                    case 9:
                        throw new Exception("同一手机号5分钟内重复提交相同的内容超过3次");
                    case 10:
                        throw new Exception("手机号黑名单过滤");
                    case 13:
                        throw new Exception("营销短信暂停发送");
                    case 17:
                        throw new Exception("24小时内同一手机号发送次数超过限制");
                    case 20:
                        throw new Exception("不支持的国家地区");
                    case 22:
                        throw new Exception("1小时内同一手机号发送次数超过限制");
                    case 23:
                        throw new Exception("发往模板支持的国家列表之外的地区");
                    default:
                        throw new Exception("发送短信失败");
                }
            } else {
                throw new Exception("发送短信失败");
            }
        }
        return ret;
    }
}
