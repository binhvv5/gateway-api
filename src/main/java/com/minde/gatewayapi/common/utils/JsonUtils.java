package com.minde.gatewayapi.common.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonUtils {
    public static String getJsonStringFromObject(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
            return StringUtils.EMPTY;
        }

        return jsonString;
    }

    /**
     *
     * @param <T>
     * @param jsonString
     * @param targetClass
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    public static <T> T getObjectFromJsonString(String jsonString, Class<T> targetClass) throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, mapper.getTypeFactory().constructType(targetClass));
    }

    /**
     *
     * @param <T>
     * @param jsonString
     * @param targetClass
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    public static <T> T getObjectFromJsonStringIgnore(String jsonString, Class<T> targetClass) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonString, mapper.getTypeFactory().constructType(targetClass));
    }


}
