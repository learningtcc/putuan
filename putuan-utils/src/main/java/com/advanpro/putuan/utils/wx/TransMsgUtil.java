package com.advanpro.putuan.utils.wx;

import com.advanpro.putuan.utils.date.DateUtils;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;

/**
 * 作者： Vance
 * 时间： 2016/9/29
 * 描述： ${todo}.
 */
public class TransMsgUtil {

    /**
     * 构造同步的消息结构
     *
     * @return
     */
    public static String buildSyncMsg() {
        Date current = DateUtils.getCurrentDate();
        byte year = (byte) (DateUtils.getDateYear(current) - 2000);
        byte month = (byte) (DateUtils.getMonth(current) + 1);
        byte day = (byte) DateUtils.getDay(current);
        byte hour = (byte) DateUtils.getHour(current);
        byte minute = (byte) DateUtils.getMinute(current);
        byte second = (byte) DateUtils.getSecond(current);
        byte[] contentByte = {0x02, 0x02, year, month, day, hour, minute, second, month, day};
        String content = Base64.encodeBase64String(contentByte);
        return content;
    }

    /**
     * 构造清除数据的消息结构
     *
     * @return
     */
    public static String buildClearMsg() {
        byte[] contentByte = {0x02, 0x04, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        String content = Base64.encodeBase64String(contentByte);
        return content;
    }
}
