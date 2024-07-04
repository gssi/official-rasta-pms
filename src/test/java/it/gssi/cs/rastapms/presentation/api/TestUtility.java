package it.gssi.cs.rastapms.presentation.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtility {

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = createObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    public static <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = createObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }
}
