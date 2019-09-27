package com.lion.gateway.server.filter;

import com.lion.gateway.server.gray.support.RibbonFilterContextHolder;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GrayFilter
 * 灰度过滤器
 *
 * @author Yanzheng
 * @date 2019/09/05
 * Copyright 2019 Yanzheng. All rights reserved.
 */
public class GrayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 灰度拦截器
        String version = exchange.getRequest().getQueryParams().getFirst("version");
        if (null != version && !version.isEmpty()) {
            // put the serviceId in `RequestContext`
            RibbonFilterContextHolder.getCurrentContext()
                    .add("version", version);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 20;
    }

}
