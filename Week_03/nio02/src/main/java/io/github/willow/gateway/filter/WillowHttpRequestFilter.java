package io.github.willow.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

public class WillowHttpRequestFilter implements WillowRequestFilter {
    @Override
    public void filter(FullHttpRequest request) {
        String s = request.content().toString(CharsetUtil.UTF_8);
        request.headers().set("filter","request-filter0");
        System.out.println(s);
    }
}
