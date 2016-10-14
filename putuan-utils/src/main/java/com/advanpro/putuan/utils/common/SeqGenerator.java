package com.advanpro.putuan.utils.common;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者： Vance
 * 时间： 2016/8/31
 * 描述： ${todo}.
 */
public class SeqGenerator {

    private static AtomicInteger value = new AtomicInteger(1);

    private static final FastDateFormat seqDateFormat = FastDateFormat.getInstance("yyMMddHHmmssSSS");

    public synchronized static String next(String prefix) {
        String seqDate = seqDateFormat.format(System.currentTimeMillis());
        String valueStr = StringUtils.leftPad(String.valueOf(RandomStringUtils.randomNumeric(2)), 2, "0");
        StringBuffer seqNo = new StringBuffer(prefix).append(seqDate).append(valueStr);
        return seqNo.toString();
    }

}
