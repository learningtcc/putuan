package com.advanpro.putuan.web.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLBrokerFactory {

    private Log logger = LogFactory.getLog(URLBrokerFactory.class);

    private String urlConfigName;
    /**
     * 版本号
     */
    private Long version = System.currentTimeMillis();

    private Map<String, URLBroker> urlBrokers = new HashMap<String, URLBroker>();

    public void init() {
        if (StringUtils.isBlank(urlConfigName)) {
            throw new RuntimeException("没有配置url.xml");
        }
        //InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(urlConfigName);
        ClassPathResource resource = new ClassPathResource(urlConfigName);
        try {
            urlBrokers = initUrlBroker(resource.getInputStream());
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Map<String, URLBroker> initUrlBroker(InputStream inputStream) {
        Map<String, URLBroker> urlBrokers = new HashMap<String, URLBroker>();
        SAXReader reader = new SAXReader();
        try {
            Document root = reader.read(inputStream);
            List<Node> urls = (List<Node>) root.selectNodes("/urlConfig/url");
            for (Node url : urls) {

                String name = url.valueOf("@name");
                Node serverUrlNode = url.selectSingleNode("severUrl");
                if (serverUrlNode == null) {
                    throw new RuntimeException("服务器URL必须配置");
                }
                String severUrl = serverUrlNode.getText();
                // path默认是空字符串
                String path = "";
                Node pathNode = url.selectSingleNode("path");
                if (pathNode != null) {
                    path = pathNode.getText();
                }

                URLBroker urlBroker = new DefaultURLBroker();
                urlBroker.setServerUrl(severUrl);
                urlBroker.setPath(path);

                List<Node> tokens = (List<Node>) url.selectNodes("tokens/token");
                for (Node token : tokens) {
                    urlBroker.addToken(token.valueOf("@name"));
                }

                urlBrokers.put(name, urlBroker);
            }
        }
        catch (DocumentException e) {
            logger.error(e.getMessage(), e);
            return urlBrokers;
        }
        return urlBrokers;
    }

    public URLBroker getUrl(String name) {
        URLBroker urlBroker = urlBrokers.get(name);
        if (urlBroker == null)
            return null;
        else
            return urlBroker.newInstance();
    }

    /**
     * @param urlConfigName the urlConfigName to set
     */
    public void setUrlConfigName(String urlConfigName) {
        this.urlConfigName = urlConfigName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
