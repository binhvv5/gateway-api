package com.minde.gatewayapi.common.utils;

import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private String logPrefix = "[{}] [ SVC] - ";
    private static final String LOG_FORMART = "[%s:%s]%s%s";
    private static final int LINE_LENGTH = 5;
    private static final int CLASSNM_LENGTH = 20;
    private static final int STACT_TRACEINDX = 2;
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    private final String className;

    public LogUtil(Class<?> clazz) {
        className = clazz.getSimpleName();
    }

    public LogUtil(Class<?> clazz, String logPrefix) {
        className = clazz.getSimpleName();
        this.logPrefix = logPrefix;
    }

    public void addLogInfo(String description) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        logger.info(log, count);
    }

    public void addLogInfo(String description, Object obj) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        logger.info(log, count, obj instanceof String ? obj : JsonUtils.getJsonStringFromObject(obj));
    }

    public void addLogInfo(String description, Object... objs) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = objs[i] instanceof String ? objs[i].toString() : JsonUtils.getJsonStringFromObject(objs[i]);
        }
        logger.info(log, count, strs);
    }

    public void addLogError(String description) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        logger.error(log, count);
    }

    public void addLogError(String description, Object obj) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        logger.error(log, count, obj instanceof String ? obj : JsonUtils.getJsonStringFromObject(obj));
    }

    public void addLogError(String description, Object... objs) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = objs[i] instanceof String ? objs[i].toString() : JsonUtils.getJsonStringFromObject(objs[i]);
        }
        logger.error(log, count, strs);
    }

    public void addLogDebug(String description) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        logger.debug(log, count);
    }

    public void addLogDebug(String description, Object obj) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        logger.info(log, count, obj instanceof String ? obj : JsonUtils.getJsonStringFromObject(obj));
    }

    public void addLogDebug(String description, Object... objs) {
        int lineNumber = Thread.currentThread().getStackTrace()[STACT_TRACEINDX].getLineNumber();
        String classNamePad = StringUtils.rightPad(className, CLASSNM_LENGTH, StringUtils.SPACE);
        String linePad = StringUtils.rightPad(Integer.toString(lineNumber), LINE_LENGTH, StringUtils.SPACE);
        String log = String.format(LOG_FORMART, classNamePad, linePad, logPrefix, description);
        String count = CommonUtil.increaseLoggingNumber();
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = objs[i] instanceof String ? objs[i].toString() : JsonUtils.getJsonStringFromObject(objs[i]);
        }
        logger.info(log, count, strs);
    }
}
