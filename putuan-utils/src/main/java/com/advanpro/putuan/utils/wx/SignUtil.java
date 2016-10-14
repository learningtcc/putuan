package com.advanpro.putuan.utils.wx;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 请求校验工具类
 *
 * @author Retina.Ye
 */
public class SignUtil {

    private static final Log logger = LogFactory.getLog(SignUtil.class);

    /**
     * 验证签名
     *
     * @param token
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
/*

        ParamCheck.throwsIllegalArgumentException(StringUtils.isBlank(token), "token为空.");
        ParamCheck.throwsIllegalArgumentException(StringUtils.isBlank(signature), "signature为空.");
        ParamCheck.throwsIllegalArgumentException(StringUtils.isBlank(timestamp), "timestamp为空.");
        ParamCheck.throwsIllegalArgumentException(StringUtils.isBlank(nonce), "nonce为空.");
*/

        String[] arr = new String[]{token, timestamp, nonce};

        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        String tmpStr = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        }
        catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
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
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
/*

    public static String getNonceStr() {
        Random random = new Random();
        return EncryptUtil.getMD5(String.valueOf(random.nextInt(10000)));
    }
*/

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 生成签名（版本2）
     * a.对所有传入参数按照字段名的ASCII码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2...）
     * 拼接成字符串string1，注意：值为空的参数不参与签名；
     * <p/>
     * b.再将得到的字符串进行sha1编码
     *
     * @param params
     * @return
     */
    public static String getSignVersion2(Map<String, String> params) {
        // 第一步(a)
        Map<String, String> tmap = MapUtil.order(params);   // ASCII码从小到大排序
        String string1 = MapUtil.mapJoin(tmap, false, false);   // 连接成URL键值对的格式
        // 第二步(b)
        return DigestUtils.shaHex(string1);
    }
}