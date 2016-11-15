package com.advanpro.putuan.web.wechat.helper;

import com.advanpro.putuan.utils.common.EncryptUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 作者： Joinly
 * 时间： 2016/9/8
 * 描述： 验证.
 */
public class VerifyUtils {

    public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
        String[] array = new String[]{token, timestamp, nonce};

        Arrays.sort(array);

        StringBuilder builder = new StringBuilder();
        for (String value : array) {
            builder.append(value);
        }

        String temp = EncryptUtils.makeSHA1(builder.toString());

        return temp != null ? temp.equalsIgnoreCase(signature) : false;
    }
}
