package com.minde.gatewayapi.common.configs.gateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidationFilter {
    public static final List<String> openApiEndpoints= List.of(
            "/auth/register",
            "/auth/login",
            "/auth/extend"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
