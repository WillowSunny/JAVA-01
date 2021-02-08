package io.github.willow.gateway;

import io.github.kimmking.gateway.inbound.HttpInboundServer;
import io.github.willow.gateway.server.WillowNettyServer;

import java.util.Arrays;

public class WillowNettyApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        doVersion1Netty();
    }

    public static void doVersion1Netty(){
        String proxyPort = System.getProperty("proxyPort","8888");
        String proxyServers = System.getProperty("proxyServers","http://localhost:8801,http://localhost:8802");
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        WillowNettyServer server = new WillowNettyServer(port, Arrays.asList(proxyServers.split(",")));
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + server.toString());
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
