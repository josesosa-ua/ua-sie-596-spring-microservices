package com.optimagrowth.gateway.filter;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";

    public static final String AUTH_TOKEN = "tmx-auth-token";

    public static final String USER_ID = "tmx-user-id";

    public static final String ORG_ID = "tmx-org-id";

    public static final String PRE_FILTER_TYPE = "pre";

    public static final String POST_FILTER_TYPE = "post";

    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        List<String> header = requestHeaders.get(CORRELATION_ID);
        return Optional.ofNullable(header).flatMap(h -> h.stream().findFirst()).orElse(null);
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
