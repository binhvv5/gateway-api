package com.minde.gatewayapi.service;



import com.minde.gatewayapi.common.utils.CommonUtil;
import com.minde.gatewayapi.dtoes.BasicResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final MessageSource  messageSource;

    /**
     *
     * @param code
     * @param locale
     * @return
     */
    public BasicResponseDto getFailResult(String code, String locale) {
        BasicResponseDto result = new BasicResponseDto();
        result.setResponseCd(CommonUtil.getMessage(messageSource, code + ".code", locale));
        result.setResponseMsg(CommonUtil.getMessage(messageSource, code + ".msg", locale));
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
        return result;
    }


    /**
     *
     */
    private void setSuccessResult(BasicResponseDto result) {
        result.setResponseCd("000000");
        result.setResponseMsg("OK.");
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
    }

    /**
     *
     */
    private void setFailResult(BasicResponseDto result, String code, String locale) {
        result.setResponseCd(CommonUtil.getMessage(messageSource, code + ".code", locale));
        result.setResponseMsg(CommonUtil.getMessage(messageSource, code + ".msg", locale));
        result.setResponseTs(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
    }


}
