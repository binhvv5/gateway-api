package com.minde.gatewayapi.common.exception;

import lombok.Getter;

@Getter
public enum ErrorAttributesKey {
    CODE("responseCd"),
    MESSAGE("responseMsg"),
    TIME("responseTs");

    private final String key;
    ErrorAttributesKey(String key) {
        this.key = key;
    }
}
