package io.github.willow.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class WillowHttpResponseFilter implements WillowResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("willow","netty-test");
    }
}
