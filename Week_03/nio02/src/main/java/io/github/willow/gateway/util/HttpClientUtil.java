package io.github.willow.gateway.util;

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

//    public static URI getUri() throws URISyntaxException {
//        URI uri = new URIBuilder()
//                .setScheme("http")
//                .setHost("localhost")
//                .setPath("/test")
//                .setPort(8888)
//                .build();
//        return uri;
//    }

    public static String doGetRequest(String url) throws IOException, URISyntaxException {
        HttpGet httpget = new HttpGet(url);
        return doGetRequest(httpget);
    }

    public static String doGetRequest(HttpGet httpget) throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
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

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        String s = doGetRequest("http://localhost:8888/test");
        System.out.println(s);
    }
}
