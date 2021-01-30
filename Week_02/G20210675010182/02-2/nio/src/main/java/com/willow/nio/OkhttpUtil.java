package com.willow.nio;

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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = OkhttpUtil.doGetUrl("http://localhost:8808/test");
                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        String response = doGetUrl("http://localhost:8808/test");
        System.out.printf(response);
        Thread.sleep(100000);
    }
}
