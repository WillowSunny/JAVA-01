package io.github.willow.gateway.inbound;

import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.github.willow.gateway.filter.WillowHttpRequestFilter;
import io.github.willow.gateway.filter.WillowHttpResponseFilter;
import io.github.willow.gateway.filter.WillowRequestFilter;
import io.github.willow.gateway.server.WillowNettyServer;
import io.github.willow.gateway.util.HttpClientUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class WillowInboundHandler extends ChannelInboundHandlerAdapter {
    WillowHttpRequestFilter filter = new WillowHttpRequestFilter();
    private final List<String> proxyServer;
    public WillowInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Long startTime = System.currentTimeMillis();
            log.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            log.info("接收到的请求url为{}", uri);
            if (uri.contains("/test")) {
                filter.filter(fullRequest);
                testVersion1(fullRequest, ctx,uri);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 指定了服务端的地址，直接转发。加上了过滤器了。
     * @param fullRequest
     * @param ctx
     * @param uri
     */
    private void testVersion1(FullHttpRequest fullRequest, ChannelHandlerContext ctx,String uri) {
        FullHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet("http://127.0.0.1:8801" + uri);
            httpGet.addHeader("willow-filter",fullRequest.headers().get("filter"));
            String value = HttpClientUtil.doGetRequest(httpGet);
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (Exception e) {
            log.error("处理测试接口出错", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开了:"+ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接上了:"+ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }
}
