package com.minde.gatewayapi.common.exception;

import lombok.Getter;

@Getter
public enum ErrorAttributesKey {
    CODE("code"),
    MESSAGE("message"),
    TIME("timestamp");

    private final String key;
    ErrorAttributesKey(String key) {
        this.key = key;
    }
}
