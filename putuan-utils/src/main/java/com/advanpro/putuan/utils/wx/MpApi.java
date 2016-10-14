package com.advanpro.putuan.utils.wx;

import com.advanpro.putuan.utils.http.LocalHttpClient;
import com.advanpro.putuan.utils.http.MyResponseHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.Map;

/**
 * 包装了访问微信接口的https
 *
 * @author Retina.Ye
 */
public class MpApi {
    private static LocalHttpClient localHttpClient = LocalHttpClient.getInstance();
    private static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
    private static Header xmlHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_XML.toString());
    private static Header multiPartHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.MULTIPART_FORM_DATA.toString());


    public static <T> T get(String url, Map<String, String> params, Class<T> clazz) {
        RequestBuilder requestBuilder = RequestBuilder.get().setHeader(jsonHeader).setUri(url);
        for (Map.Entry<String, String> data : params.entrySet()) {
            requestBuilder.addParameter(data.getKey(), data.getValue());
        }
        HttpUriRequest httpUriRequest = requestBuilder.build();
        return localHttpClient.execute(httpUriRequest, MyResponseHandler.createResponseHandler(clazz));
    }

    public static <T> T postJson(String url, String json, Class<T> clazz) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(url)
                .setEntity(new StringEntity(json, Charset.forName("utf-8")))
                .build();
        return localHttpClient.execute(httpUriRequest, MyResponseHandler.createResponseHandler(clazz));
    }

    public static <T> T post(String url, String accessToken, String json, Class<T> clazz) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(jsonHeader)
                .setUri(url)
                .addParameter("access_token", accessToken)
                .setEntity(new StringEntity(json, Charset.forName("utf-8")))
                .build();
        return localHttpClient.execute(httpUriRequest, MyResponseHandler.createResponseHandler(clazz));
    }

    public static <T> T postXml(String url, String xml, Class<T> clazz) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(xmlHeader)
                .setUri(url)
                .setEntity(new StringEntity(xml, Charset.forName("utf-8")))
                .build();
        return localHttpClient.execute(httpUriRequest, MyResponseHandler.createXMLResponseHandler(clazz));
    }

    public static String post(String url, String xml) {
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(xmlHeader)
                .setUri(url)
                .setEntity(new StringEntity(xml, Charset.forName("utf-8")))
                .build();
        return (String) localHttpClient.execute(httpUriRequest, MyResponseHandler.createStringResponseHandler());
    }

    public static <T> T postFile(String url, File file, Class<T> clazz) {
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("media", file, ContentType.MULTIPART_FORM_DATA, file.getName())
                .build();
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(multiPartHeader)
                .setUri(url)
                .setEntity(entity)
                .build();
        return localHttpClient.execute(httpUriRequest, MyResponseHandler.createResponseHandler(clazz));
    }

    public static <T> T SSLGet(String url, String mchId, String xml, Class<T> clazz) {
        InputStream inputStream = null;
        try {
            ClassPathResource resource = new ClassPathResource("ssl/apiclient_cert.p12");
            inputStream = resource.getInputStream();

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, mchId.toCharArray());

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            HttpUriRequest httpUriRequest = RequestBuilder.post()
                    .setHeader(xmlHeader)
                    .setUri(url)
                    .setEntity(new StringEntity(xml, Charset.forName("utf-8")))
                    .build();
            return httpclient.execute(httpUriRequest, MyResponseHandler.createXMLResponseHandler(clazz));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);

        }
    }
}