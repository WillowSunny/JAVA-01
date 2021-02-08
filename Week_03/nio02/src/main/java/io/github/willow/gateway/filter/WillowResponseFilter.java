package io.github.willow.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public interface WillowResponseFilter {
    void filter(FullHttpResponse response);
}
