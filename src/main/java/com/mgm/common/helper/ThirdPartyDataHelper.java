package com.mgm.common.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mgm.common.serialization.Serialization;

import java.util.*;
import static com.mgm.common.helper.GeneralHelper.isNullOrEmpty;

public class ThirdPartyDataHelper {
    public static String getText(JsonNode node, String key) {
        if (!isNullOrEmpty(node) && !isNullOrEmpty(node.get(key))) {
            return node.get(key).asText();
        }
        return "";
    }

    public static Optional<String> getTextOptional(JsonNode node, String key) {
        if (!isNullOrEmpty(node) && !isNullOrEmpty(node.get(key))) {
            return Optional.of(node.get(key).asText());
        }
        return Optional.empty();
    }

    public static Boolean getBoolean(JsonNode node, String key) {
        return getBoolean(node, key, false);
    }

    public static Boolean getBoolean(JsonNode node, String key, Boolean defaultValue) {
        if (!isNullOrEmpty(node) && !isNullOrEmpty(node.get(key))) {
            return node.get(key).asBoolean();
        }
        return defaultValue;
    }

    public static Integer getInteger(JsonNode node, String key) {
        if (!isNullOrEmpty(node) && !isNullOrEmpty(node.get(key))) {
            return node.get(key).asInt();
        }
        return 0;
    }

    public static Long getLong(JsonNode node, String key) {
        if (!isNullOrEmpty(node) && !isNullOrEmpty(node.get(key))) {
            return node.get(key).asLong();
        }
        return 0L;
    }

    public static Double getDouble(JsonNode node, String key) {
        if (!isNullOrEmpty(node) && !isNullOrEmpty(node.get(key))) {
            return node.get(key).asDouble();
        }
        return 0.0;
    }

    public static JsonNode getNode(JsonNode node, String key) {
        if (node != null) {
            return node.get(key);
        }
        return null;
    }

    public static Optional<JsonNode> getNodeOptional(JsonNode node, String key) {
        if (node != null) {
            return Optional.ofNullable(node.get(key));
        }
        return Optional.empty();
    }
    

    public static List<String> getTextListFromArrayNode(ArrayNode arrayNode, String key) {
        List<String> textList = new ArrayList<>();
        try {
            if (!isNullOrEmpty(arrayNode)) {
                for (JsonNode jsonNode : arrayNode) {
                    textList.add(getText(jsonNode, key));
                }
            }
            return textList;
        } catch (Exception e) {
            return textList;
        }
    }

    public static String getTextFromFirstInArrayNode(ArrayNode arrayNode, String key) {
        try {
            if (!isNullOrEmpty(arrayNode)) {
                return getText(arrayNode.get(0), key);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<ObjectNode> flatByKey(List<Object> objects, String key) {
        List<ObjectNode> newObjects = new ArrayList<>();
        for (Object obj : objects) {
            ObjectNode noteNode = Serialization.serializeToObjectNode(obj);
            JsonNode fields = noteNode.get(key);
            if (fields.isArray() && !fields.isEmpty()) {
                for (Object field : fields) {
                    newObjects.add(Serialization.serializeToObjectNode(field));
                }
            }
        }
        return newObjects;
    }
}
