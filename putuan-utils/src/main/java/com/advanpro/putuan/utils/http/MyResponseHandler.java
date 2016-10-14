package com.advanpro.putuan.utils.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Writer;

public class MyResponseHandler {

    private static final Log logger = LogFactory.getLog(MyResponseHandler.class);

    public static <T> ResponseHandler<T> createResponseHandler(final Class<T> clazz) {
        return new ResponseHandler<T>() {
            @Override
            public T handleResponse(HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String str = EntityUtils.toString(entity);
                    logger.info("createResponseHandler:" + str);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    return objectMapper.readValue(new String(str.getBytes("iso-8859-1"), "utf-8"), clazz);
                }
                else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
    }

    public static <T> ResponseHandler<T> createXMLResponseHandler(final Class<T> clazz) {
        return new ResponseHandler<T>() {
            @Override
            public T handleResponse(HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String str = EntityUtils.toString(entity);
                    logger.info("createXMLResponseHandler:" + str);
                    xstream.alias("xml", clazz);
                    return (T) xstream.fromXML(new String(str.getBytes("iso-8859-1"), "utf-8"));
                }
                else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
    }

    public static <T> ResponseHandler<T> createStringResponseHandler() {
        return new ResponseHandler<T>() {
            @Override
            public T handleResponse(HttpResponse response) throws IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    String str = EntityUtils.toString(entity);
                    logger.info("createStringResponseHandler:" + str);
                    return (T) new String(str.getBytes("iso-8859-1"), "utf-8");
                }
                else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
    }

    /** 扩展xstream，使其支持CDATA块 */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                    else {
                        writer.write(text);
                    }
                }
            };
        }
    });

}