package com.willow.nio;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpClientUtil {

    public static URI getUri() throws URISyntaxException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8808)
                .setPath("/test")
                .build();
        return uri;
    }

    public static String doGetRequest() throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(getUri());
        CloseableHttpResponse response = httpclient.execute(httpget);
        String res = "";
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    res = EntityUtils.toString(entity);
                } finally {
                    instream.close();
                }
            }
        } finally {
            response.close();
        }
        return res;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        String response = doGetRequest();
        System.out.println(response);
    }
}
