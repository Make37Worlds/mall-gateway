package com.example.mallgateway;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mallgateway.util.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.mallgateway.util.JwtUtils.verifyToken;

public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    private final List<String> permitAllPaths = List.of("/api/mallmember/login");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();
        // Check if the path is in the permit all list
        if (permitAllPaths.contains(path)) {

            return chain.filter(exchange); // Skip JWT check
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        DecodedJWT jwt = verifyToken(token, "src/main/java/com/example/mallgateway/keys/public_key.pem");
        if (jwt == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extract the user ID from the token
        String userId = jwt.getClaim("userId").asString();

        // Add the user ID to the request headers
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-User-ID", userId)
                .build();

        // Forward the modified request
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }


    @Override
    public int getOrder() {
        return -100; // 设置过滤器的优先级
    }
}
