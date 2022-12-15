package com.minde.gatewayapi.common.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import java.util.Locale;

public class CommonUtil {

    private final LogUtil logger = new LogUtil(this.getClass());
    private static final String FULL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
    private static final String NUMBER_CHARACTERS = "0123456789";
    private static final String NUMBER_AND_STRING_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String currentProfile(Environment environment) {

        String[] profiles = environment.getActiveProfiles();
        if (profiles.length == 0) {
            profiles = environment.getDefaultProfiles();
        }
        String profile = profiles[0];

        return profile;
    }


    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;

    }

    public static String camelCaseToUnderscores(String camel) {
        String underscore;
        underscore = String.valueOf(Character.toLowerCase(camel.charAt(0)));
        for (int i = 1; i < camel.length(); i++) {
            underscore += Character.isLowerCase(camel.charAt(i)) ? String.valueOf(camel.charAt(i))
                    : "_" + String.valueOf(Character.toLowerCase(camel.charAt(i)));
        }
        return underscore;
    }

    public static String getMessage(MessageSource messageSource, String code, String locale) {

        return messageSource.getMessage(code, null, Locale.forLanguageTag(locale));
    }



    public static String increaseLoggingNumber() {
        int cnt = MDC.get("log.cnt") == null ? 1 : Integer.parseInt(MDC.get("log.cnt").replaceAll(" ", ""));
        cnt++;
        String strCnt = String.valueOf(cnt);

        if (strCnt.length() == 1) {
            strCnt = "  " + strCnt;
        } else if (strCnt.length() == 2) {
            strCnt = " " + strCnt;
        }

        MDC.put("log.cnt", strCnt);

        return MDC.get("log.cnt");
    }

    public static String createRandomPassword() {
        return RandomStringUtils.random(16, NUMBER_AND_STRING_CHARACTERS);
    }

    public static String createRandomCode() {
        return RandomStringUtils.random(6, NUMBER_CHARACTERS);
    }

    public static String createRandom32LengthString() {
        return RandomStringUtils.random(32, NUMBER_AND_STRING_CHARACTERS);
    }
}
