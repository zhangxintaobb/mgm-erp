package com.mgm.common.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Serialization {
    @Value
    @JsonSerialize
    public static class EmptyObject {
    }

    public static final EmptyObject EMPTY_OBJECT = new EmptyObject();

    private static final ObjectMapper objectMapper;

    private static final ObjectMapper snakeCaseObjectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        // Ignore unknown properties globally
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // @TODO To be deprecated, recommend to use POJO class for this case
        snakeCaseObjectMapper = new ObjectMapper();
        snakeCaseObjectMapper.registerModule(new Jdk8Module());
        snakeCaseObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        snakeCaseObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public static ObjectMapper getMapper() {
        // TODO add a pool to prevent locking here.
        return objectMapper;
    }

    private static ObjectMapper getSnakeCaseObjectMapper() {
        // TODO add a pool to prevent locking here.
        return snakeCaseObjectMapper;
    }

    public static String serialize(Object obj) throws JsonProcessingException {
        try {
            String str = getMapper().writeValueAsString(obj);
            return str;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static ObjectNode serializeToObjectNode(Object obj) throws IllegalArgumentException {
        try {
            ObjectNode str = getMapper().valueToTree(obj);
            return str;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    // @TODO To be deprecated, recommend to use POJO class for this case
    public static ObjectNode serializeToSnakeCaseObjectNode(Object obj) throws IllegalArgumentException {
        try {
            ObjectNode str = getSnakeCaseObjectMapper().valueToTree(obj);
            return str;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static <TObject extends Object> TObject deserialize(String str, Class<TObject> clazz) throws JsonProcessingException, JsonMappingException {
        try {
            TObject obj = getMapper().readValue(str, clazz);
            return obj;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static <T> List<T> deserializeToList(String str, TypeReference<List<T>> typeReference) throws JsonProcessingException {
        return getMapper().readValue(str, typeReference);
    }

    public static <TObject extends Object> TObject deserializeSilent(String str, Class<TObject> clazz) {
        try {
            TObject obj = getMapper().readValue(str, clazz);
            return obj;
        } catch (Exception exception) {
            log.error("deserializeSilent error", exception);
            return null;
        }
    }

    public static <TObject extends Object> Map<String, List<TObject>> deserializeToMapObjList(String str) {
        try {
            Map<String, List<TObject>> obj = getMapper().readValue(str, Map.class);
            return obj;
        } catch (Exception exception) {
            return new HashMap<>();
        }
    }

    public static List<String> deserializeToListString(String str) throws JsonProcessingException, JsonMappingException {
        try {
            List<String> obj = getMapper().readValue(str, List.class);
            return obj;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static <TObject extends Object> List<TObject> deserializeToListObj(String str) throws JsonProcessingException, JsonMappingException {
        try {
            List<TObject> obj = getMapper().readValue(str, List.class);
            return obj;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static Map<String, Object> deserializeToMapStringObj(String str) throws JsonProcessingException, JsonMappingException {
        try {
            Map<String, Object> obj = getMapper().readValue(str, Map.class);
            return obj;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static Map<String, List<String>> deserializeToMapStringList(String str) {
        try {
            Map<String, List<String>> obj = getMapper().readValue(str, Map.class);
            return obj;
        } catch (Exception exception) {
            // do nothing
            return new HashMap<>();
        }
    }

    public static Map<String, Integer> deserializeToMapStringInteger(String str) throws JsonProcessingException, JsonMappingException {
        try {
            Map<String, Integer> obj = getMapper().readValue(str, Map.class);
            return obj;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static Map<String, String> deserializeToMapString(String str) throws JsonProcessingException, JsonMappingException {
        try {
            Map<String, String> obj = getMapper().readValue(str, Map.class);
            return obj;
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }


    public static ArrayNode makeArrayNode(List<String> arr) {
        var arrNode = JsonNodeFactory.instance.arrayNode();
        for (var i = 0; i < arr.size(); i++) {
            arrNode.add(arr.get(i));
        }
        return arrNode;
    }

    public static ArrayNode makeIntegerListArrayNode(List<Integer> arr) {
        var arrNode = JsonNodeFactory.instance.arrayNode();
        for (var i = 0; i < arr.size(); i++) {
            arrNode.add(arr.get(i));
        }
        return arrNode;
    }

    public static <T> T convertArrayJsonNodeToList(JsonNode jsonNode, TypeReference<T> valueTypeRef) throws Exception {
        try {
            if (jsonNode.isArray()) {
                String json = getMapper().writeValueAsString(jsonNode);
                return getMapper().readValue(json, valueTypeRef);
            } else {
                return null;
            }
        } catch (Exception exception) {
            // do nothing
            throw exception;
        }
    }

    public static ArrayNode makeListJsonArrayNode(List<JsonNode> arr) {
        var arrNode = JsonNodeFactory.instance.arrayNode();
        for (var i = 0; i < arr.size(); i++) {
            arrNode.add(arr.get(i));
        }
        return arrNode;
    }

    public static ArrayNode makeObjectNodeListArrayNode(List<ObjectNode> arr) {
        var arrNode = JsonNodeFactory.instance.arrayNode();
        for (var i = 0; i < arr.size(); i++) {
            arrNode.add(arr.get(i));
        }
        return arrNode;
    }
}
