package com.advanpro.putuan.utils.xml;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 作者： Vance
 * 时间： 2016/9/23
 * 描述： ${todo}.
 */
public class XMLReadConfig {

    static {
        InputStream inputStream = XMLReadConfig.class.getClassLoader().getResourceAsStream("fileupload.xml");

        try {
            readConfig(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


    public static void readConfig(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();

        try {
            Document e = saxReader.read(inputStream);
           // uploadPath = StringUtils.trim(e.selectSingleNode("/config/local/uploadPath").getText());
           // localDomain = StringUtils.trim(e.selectSingleNode("/config/local/domain").getText());
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException("解析配置文件出错.", var3);
        }
    }
}
