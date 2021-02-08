package io.github.willow.gateway.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkhttpUtil {

    public static OkHttpClient client = new OkHttpClient();

    public static String doGetUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String response = doGetUrl("http://localhost:8889/test");
        System.out.println(response);
    }
}
