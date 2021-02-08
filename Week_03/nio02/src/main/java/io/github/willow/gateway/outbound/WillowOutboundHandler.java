package io.github.willow.gateway.outbound;

import io.github.kimmking.gateway.filter.HeaderHttpResponseFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.github.willow.gateway.filter.WillowHttpResponseFilter;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WillowOutboundHandler extends ChannelOutboundHandlerAdapter {
    WillowHttpResponseFilter filter = new WillowHttpResponseFilter();

    private final List<String> proxyServer;
    public WillowOutboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("outbound write 方法 执行");
        FullHttpResponse response = (FullHttpResponse) msg;
        filter.filter(response);
        super.write(ctx, msg, promise);
    }
}
