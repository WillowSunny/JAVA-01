package io.github.willow.gateway.server;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

// 单线程的socket程序
public class HttpServer01 {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                service(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void service(Socket socket) {
        try {
//            Thread.sleep(5);
            /**
             * 不管是请求消息还是相应消息，都可以划分为三部分，这就为我们后面的处理简化了很多
             *
             * 第一行：状态行
             * 第二行到第一个空行：header（请求头/相应头)
             * 剩下所有：正文
             */
            BufferedReader httpReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            String[] strs = StringUtils.split(httpReader.readLine(), " ");
            System.out.println(strs[0]);
            System.out.println(strs[1]);
            System.out.println(strs[2]);

            Map<String, String> headers = new HashMap<>(16);
            String line = httpReader.readLine();
            String[] kv;
            while (!"".equals(line)) {
                kv = StringUtils.split(line, ":");
                assert kv.length == 2;
                headers.put(kv[0].trim(), kv[1].trim());
                System.out.println(line);
                line = httpReader.readLine();
            }

            ///--------------------------
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio1";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();//区分请求头和请求体的空白行，必须要有。哎，这网络基础是真的差OMZ
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) { // | InterruptedException e) {
            e.printStackTrace();
        }
    }
}