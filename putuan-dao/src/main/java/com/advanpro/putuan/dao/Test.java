package com.advanpro.putuan.dao;

import com.advanpro.putuan.model.User;
import com.advanpro.putuan.model.type.DeviceType;
import com.advanpro.putuan.utils.cache.RedisCacheService;
import com.advanpro.putuan.utils.cache.impl.RedisCacheServiceImpl;
import com.advanpro.putuan.utils.date.DateUtils;
import com.advanpro.putuan.utils.json.JsonUtils;
import com.advanpro.putuan.utils.token.TokenProcessor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者： Vance
 * 时间： 2016/8/31
 * 描述： ${todo}.
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
   /*     //测试多线程性能
        System.out.println("--------测试多线程性能------------------");
        ExecutorService es1 = Executors.newFixedThreadPool(500);
        final Set<String> idSet = Collections.synchronizedSet(new HashSet<String>());
        long t1 = System.currentTimeMillis();

        for (int i = 0; i < 500; i++) {
            es1.submit(new Runnable() {
                @Override
                public void run() {
                    int count = 0;
                    while (count < 100) {
                        String val = SeqGenerator.next("PT");
                        if (idSet.contains(val)) {
                            System.out.println("重复了: " + val);
                        } else {
                            idSet.add(val);
                        }
                        count++;
                    }
                }
            });
        }
        es1.shutdown();
        System.out.println("启用顺序关闭");
        while (true) {
            if (es1.isTerminated()) {
                System.out.println("所有的子线程都结束了！");
                break;
            }
            try {
                System.out.println("子线程的任务还没运行完");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("500线程,每个线程生成10000个序列号.共耗时: " + (System.currentTimeMillis() - t1) + " ms");*/
        Object ab = null;
        String b = (String) ab;


       List<Integer> a = Lists.newArrayList(new Integer[]{0});
        TokenProcessor tokenProcessor = new TokenProcessor();
        tokenProcessor.setSecretKey("<putuan>");
        File folder = new File("/11113" + "/fdsfdf");
        folder.mkdirs();
        System.out.println(tokenProcessor.getTokenV("13750024022:ca97fa3d1f2d403ca0dcb8e067c6693f"));



/*        Date current = DateUtils.getCurrentDate();
        byte year = (byte) (DateUtils.getDateYear(current) - 2000);
        byte month = (byte) (DateUtils.getMonth(current) + 1);
        byte day = (byte) DateUtils.getDay(current);
        byte hour = (byte) DateUtils.getHour(current);
        byte minute = (byte) DateUtils.getMinute(current);
        byte second = (byte) DateUtils.getSecond(current);
        byte[] contentByte = {0x02, 0x02, year, month, day, hour, minute, second, month, day};*/

/*        System.out.println(Base64.encodeBase64String(contentByte));
        System.out.println(new String(Base64.encodeBase64(contentByte)));*/

        byte f = (byte) 0xFF;
        byte[] aa = intToByteArray(255);
        byte[] d = {0x02, 0x04};
        int c = byteArrayToInt(aa);
        int kneelCount = ((d[0] & 0x000000FF) << 8) + (d[1] & 0x000000FF);
        DeviceType type = DeviceType.valueOf("KD");
        System.out.println(type.desc());

        RedisCacheService redisCacheService = new RedisCacheServiceImpl("127.0.0.1:6379");
       // redisCacheService.put("testvance", "gdsgdsgdsgds", 5);
        String test = (String)redisCacheService.get("testvance");
        System.out.println(test);



    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        //由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static int byte2int(byte[] res) {
// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        //由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;//往高位游
        }
        return value;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("token", "13750024022:9f32f1c2d660493c8e7d2fc551e9e1d3");
            conn.setRequestProperty("tokenV", "cffeddb1fd2073d30d02e4afd8d39afbf2d0e6eb");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type: text/plain" + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

}

