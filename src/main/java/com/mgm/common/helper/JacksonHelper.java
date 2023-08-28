package com.mgm.common.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mgm.common.serialization.Serialization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mgm.common.helper.GeneralHelper.*;
import static com.mgm.common.helper.ThirdPartyDataHelper.*;

public class JacksonHelper {

    public static ObjectNode makeStringNode(String key, String val) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put(key, val);
        return result;
    }

    public static ObjectNode makeStringListNode(String key, List<String> val) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set(key, Serialization.makeArrayNode(val));
        return result;
    }

    public static ObjectNode makeParentNode(String key, ObjectNode child) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set(key, child);
        return result;
    }

    public static ObjectNode makeParentNode(String key, ArrayNode child) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set(key, child);
        return result;
    }

    public static Map<String, Integer> arrayNodeToMap(ArrayNode nodes, String keyName, String valueName) {
        Map<String,Integer> resultMap = new HashMap<>();
        if (isNullOrEmpty(nodes)) {
            return resultMap;
        }
        for (JsonNode node: nodes) {
            String key = getText(node, keyName);
            Integer value = getInteger(node, valueName);
            if (isNullOrEmpty(key) || value == 0) {
                continue;
            }
            resultMap.put(trimLowerString(key), value);
        }
        return resultMap;
    }
}
