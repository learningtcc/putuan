package com.advanpro.putuan.web.wechat.helper;

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

        MessageDigest md = null;
        String temp = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] temByte = md.digest(builder.toString().getBytes());
            temp = byteToString(temByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        builder = null;
        return temp != null ? temp.equals(signature.toUpperCase()) : false;
    }

    private static String byteToString(byte[] array) {
        String strDigest = "";
        for (int i = 0; i < array.length; i++) {
            strDigest += byteToHexStr(array[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
}
