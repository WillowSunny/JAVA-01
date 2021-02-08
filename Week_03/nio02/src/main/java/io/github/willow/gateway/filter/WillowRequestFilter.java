package io.github.willow.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface WillowRequestFilter {
    void filter(FullHttpRequest response);
}
