package com.minde.gatewayapi.common.configs.gateway.filter;

import com.minde.gatewayapi.common.configs.gateway.provider.JWTProvider;
import com.minde.gatewayapi.common.utils.LogUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.*;

@RefreshScope
@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalCustomFilter implements GatewayFilter {
    private static final LogUtil loggerReq = new LogUtil(GlobalCustomFilter.class, "[{}] [ REQ] - ");
    private static final LogUtil loggerRes = new LogUtil(GlobalCustomFilter.class, "[{}] [ RES] - ");
    private final RouterValidationFilter routerValidation;
    private final JWTProvider jwtProvider;
    private static long start;
    private static long end;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            start = System.currentTimeMillis();
            ServerHttpRequest request = exchange.getRequest();
            MDC.put("trace.id", UUID.randomUUID().toString());
            MDC.put("log.cnt", "0");
            loggingRequest(request);
            if (routerValidation.isSecured.test(request)) {
                if (this.isAuthMissing(request))
                    return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

                String token = this.getAuthHeader(request);
                if (!token.trim().isEmpty()) {
                    token = token.replace("Bearer ", "");
                }
                if (jwtProvider.isInvalid(token))
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);

                this.populateRequestWithHeaders(exchange, token);
            }

            return chain.filter(exchange);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            end = System.currentTimeMillis();
            loggingResponse(exchange.getResponse());
            MDC.clear();
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtProvider.getClaimsJwtToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }

    private void loggingRequest(ServerHttpRequest request) throws IOException {
        loggerReq.addLogInfo("Remote IP 		: {}", request.getRemoteAddress().getHostString());
        loggerReq.addLogInfo("Method 		    : {}", request.getMethod().name());
        loggerReq.addLogInfo("URI 		    : {}", request.getURI().getPath());
        loggerReq.addLogInfo("Headers 		: {}", getHeaders(request.getHeaders()));
//        loggerReq.addLogInfo("Payload 		: {}", logPayload(request.getHeaders().getContentType(), readAsInputStream(request.getBody())).replaceAll("\\R", "").replaceAll(" ", ""));
    }

    private void loggingResponse(ServerHttpResponse response) {
        loggerRes.addLogInfo("Status 		: {}", response.getStatusCode().value());
        loggerRes.addLogInfo("Processing Time	: {}", (end - start) / 1000.0);

//        loggerRes.addLogInfo("Payload 		: {}", response.get);
    }

    private static boolean isVisible(MediaType mediaType) {
        final List<MediaType> VISIBLE_TYPES = Arrays.asList(
                MediaType.valueOf("text/*"),
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/*+json"),
                MediaType.valueOf("application/*+xml"),
                MediaType.MULTIPART_FORM_DATA
        );

        return VISIBLE_TYPES.stream()
                .anyMatch(visibleType -> visibleType.includes(mediaType));
    }

    private static String logPayload(MediaType mediaType, InputStream inputStream) throws IOException {

        String contentString = "";
        boolean visible = isVisible(mediaType);
        if (visible) {
            byte[] content = StreamUtils.copyToByteArray(inputStream);
            if (content.length > 0) {
                contentString = new String(content);
            }
        } else {
            contentString = "Binary Content";
        }

        return contentString;
    }


    private InputStream readAsInputStream(Flux<DataBuffer> body) throws IOException {
        PipedOutputStream osPipe = new PipedOutputStream();
        PipedInputStream isPipe = new PipedInputStream(osPipe);

        DataBufferUtils.write(body, osPipe)
                .subscribe(DataBufferUtils.releaseConsumer());
        return isPipe;
    }

    private static Map getHeaders(HttpHeaders headers) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", headers.getContentType() != null ? headers.getContentType().toString() : "");
        headerMap.put("User-Agent", headers.get("User-Agent") != null && headers.get("User-Agent").isEmpty() ? headers.get("User-Agent").get(0) : "");
        headerMap.put("Content-Length", headers.get("Content-Length") == null || headers.get("Content-Length").isEmpty() ? "" : headers.get("Content-Length").get(0));
        return headerMap;
    }
}
