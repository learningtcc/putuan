package com.advanpro.putuan.utils.check;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者： Joinly
 * 时间： 2015/10/27
 * 描述： todo.
 */
public class CheckUtils {
    private final static String mobile_regex = "^1[3-8]+\\d{9}"; //"^1(3[0-9]|5[0-35-9]|8[0-9]|14[57])[0-9]{8}$";
    private final static String email_regex = "^\\w\\S*@([a-zA-Z0-9\\-]+\\.)+[a-zA-Z0-9]{2,4}$";

    /**
     * 验证电话
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile) {
        if (mobile == null || mobile.length() == 0)
            return false;
        Pattern pattern = Pattern.compile(mobile_regex);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if (email == null || email.length() == 0)
            return false;
        Pattern pattern = Pattern.compile(email_regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证第三方登录账号
     */
    public static boolean checkThridAccount(String account) {
        if (account == null || account.length() == 0)
            return false;
        return (account.endsWith("&weixin") || account.endsWith("&qq") || account.endsWith("&sinaweibo"));
    }

    /**
     * 验证第三方密码
     * @param thirdAcccount
     * @param md5Password 加密的密码
     * @return
     */
    public static boolean checkThridAccountPassword(String thirdAcccount, String md5Password) {
        String password = makeThridAccountPassword(thirdAcccount);
        return makeMd5(password).equalsIgnoreCase(md5Password);
    }

    /**
     * 验证账号
     */
    public static boolean checkAccount(String account) {
        return (checkMobile(account) || checkEmail(account) || checkThridAccount(account));
    }

    /**
     * 验证性别
     */
    public static boolean checkGender(String gender) {
        if (gender == null || gender.length() == 0)
            return false;
        return gender.equals("M") || gender.equals("F");
    }

    /**
     * 验证设备ID格式
     */
    public static boolean checkDeviceId(String id) {
        return id != null && id.length() == 18 && id.matches("^[A-Za-z0-9]{6}[A-Z][0-9]+$");
    }

    /**
     * 生成第三方账号密码
     * @param thirdAcccount 第三方账号，以&xxx结尾
     * @return 返回密码
     */
    public static String makeThridAccountPassword(String thirdAcccount) {
        String prefix = "c3fff95a1c15423c9b1379442b5c4348";
        return makeMd5(prefix + thirdAcccount);
    }

    public static String makeMd5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            BigInteger bi = new BigInteger(1, md5.digest());
            return bi.toString(16);
        } catch (Exception e) {
            return "";
        }
    }
}
