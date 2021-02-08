package io.github.willow.gateway.inbound;

import io.github.kimmking.gateway.inbound.HttpInboundHandler;
import io.github.willow.gateway.outbound.WillowOutboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;


public class WillowInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> proxyServers;

    public WillowInboundInitializer(List<String> proxyServers) {
        this.proxyServers = proxyServers;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new WillowOutboundHandler(this.proxyServers));
        pipeline.addLast(new WillowInboundHandler(this.proxyServers));
    }
}
