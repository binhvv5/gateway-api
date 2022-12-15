package com.minde.gatewayapi.common.exception;




import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

	private final String errorCode;
	private final String locale;
	
	public AuthenticationException(String errorCode) {
		this.errorCode = errorCode;
		this.locale = "en";
	}
	
	public AuthenticationException(String errorCode, String locale) {
		this.errorCode = errorCode;
		this.locale = locale;
	}
}
