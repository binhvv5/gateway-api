package com.minde.gatewayapi.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final String errorCode;
	private final String locale;
	
	public CustomException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.locale = "en";
	}
	
	public CustomException(String message, String errorCode, String locale) {
		super(message);
		this.errorCode = errorCode;
		this.locale = locale;
	}
}
