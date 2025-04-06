package com.optimagrowth.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Order(1)
@Component
public class TrackingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    FilterUtils filterUtils;

    public TrackingFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            if (logger.isDebugEnabled()) {
                logger.debug("tmx-correlation-id found in tracking filter: {}. ",
                        filterUtils.getCorrelationId(requestHeaders));
            }
        }
        else {
            String correlationID = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationID);
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", correlationID);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("The authentication name from the token is : {}", getUsername(requestHeaders));
        }

        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtils.getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

    private String getUsername(HttpHeaders requestHeaders) {
        return Optional.ofNullable(filterUtils.getAuthToken(requestHeaders))
            .map(token -> token.replace("Bearer ", ""))
            .map(this::decodeJWT)
            .map(json -> json.get("preferred_username").asText())
            .orElse("");
    }

    private JsonNode decodeJWT(String jwtToken) {
        try {
            String[] chunks = jwtToken.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(chunks[1]), StandardCharsets.UTF_8);
            return new ObjectMapper().readTree(payload);
        }
        catch (Exception e) {
            logger.error("Error decoding JWT", e);
            return null;
        }
    }

}