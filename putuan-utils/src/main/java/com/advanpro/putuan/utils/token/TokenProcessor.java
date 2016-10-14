/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advanpro.putuan.utils.token;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TokenProcessor {
    private String secretKey;
    private String algorithm = "SHA1";

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    //计算字符串摘要
    private byte[] secret(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(str.getBytes("UTF8"));
        return messageDigest.digest();
    }

    // 把密文转换成十六进制的字符串形式
    private String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    private String buildSecretToken(String token) {
        StringBuilder secretToken = new StringBuilder(secretKey.length() + token.length() + 1);
        return secretToken.append(secretKey).append(token).toString();
    }

    /**
     * 哈希后分配给用户的TokenV,用来防止token伪造与篡改
     *
     * @param token 分配给用户的Token
     * @return TokenV
     */
    public String getTokenV(String token) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String secretToken = buildSecretToken(token);
        byte[] secret = secret(secretToken);
        return getFormattedText(secret);
    }

    /**
     * 验证token是否经过篡改，并防止token伪造
     *
     * @param token  来自分配给用户的Token
     * @param tokenV 哈希后分配给用户的TokenV
     * @return true-通过验证;false-未能通过验证
     * @throws NoSuchAlgorithmException
     */
    public boolean isCorrect(String token, String tokenV) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String tokenVx = getTokenV(token);
        return tokenVx.equals(tokenV);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String generateToken(String prefix) {
        return prefix + ":" + UUID.randomUUID().toString().replaceAll("-", "");
    }
}
