package com.advanpro.putuan.utils.common;

import com.advanpro.putuan.utils.token.TokenProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者： Vance
 * 时间： 2016/11/3
 * 描述： 加密类.
 */
public class EncryptUtils {

    private static MessageDigest md5 = null;

    private static MessageDigest sha1 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成MD5编码
     *
     * @param str
     * @return
     */
    public static String makeMd5(String str) {
        md5.update(str.getBytes());
        BigInteger bi = new BigInteger(1, md5.digest());
        return bi.toString(16);
    }

    /**
     * 生成文件MD5编码
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        md5.update(byteBuffer);
        BigInteger bi = new BigInteger(1, md5.digest());
        return bi.toString(16);
    }


    /**
     * 生成SHA1编码
     *
     * @param str
     * @return
     */
    public static String makeSHA1(String str) {
        sha1.update(str.getBytes());
        BigInteger bi = new BigInteger(1, sha1.digest());
        return bi.toString(16);
    }


    public static void main(String... args) {
        System.out.println("==" + EncryptUtils.makeMd5("adv123"));
        TokenProcessor tokenProcessor = new TokenProcessor();
        tokenProcessor.setSecretKey("<putuan>");
        try {
            System.out.println(tokenProcessor.getTokenV("13750024086:0ca1b59e5a014e6abaa401ffcf581cf2"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
