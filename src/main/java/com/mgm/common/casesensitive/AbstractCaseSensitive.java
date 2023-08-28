package com.mgm.common.casesensitive;


import com.mgm.common.serialization.Serialization;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AbstractCaseSensitive<T> {

    public static final String STR = "java.lang.String";
    public static final String OPTIONAL_STR = "java.util.Optional<java.lang.String>";
    public static final String LIST_STR = "java.util.List<java.lang.String>";
    public static final String OPTIONAL_LIST_STR = "java.util.Optional<java.util.List<java.lang.String>>";

    public static final Map<String, Class<?>> TRANSFER_CLASSES = Map.of(
            STR, String.class,
            OPTIONAL_STR, Optional.class,
            LIST_STR, List.class,
            OPTIONAL_LIST_STR, Optional.class);



    Object getValueAfterChange(Object originValue, String typeName, CaseSensitiveType type, Class<T> valueType, String fieldName) {
        try {
            if (Optional.empty().equals(originValue)) {
                return Optional.empty();
            }
            if (TRANSFER_CLASSES.containsKey(typeName)) {
                return getNewValue(typeName, type, originValue);
            }
        } catch (Exception ex) {
            log.error(String.format("CaseSensitive change error in valueType=%s, fieldName=%s", valueType, fieldName), ex);
        }
        return originValue;

    }

    Object getNewValue(String typeName, CaseSensitiveType type, Object value) throws Exception {
        String serialize = Serialization.serialize(value);
        Class<?> toClass = TRANSFER_CLASSES.get(typeName);
        String toValue = caseChange(serialize, type);
        return Serialization.deserialize(toValue, toClass);
    }

    String caseChange(String value, CaseSensitiveType type) {
        switch (type) {
            case LOWER:
                return value.toLowerCase();
            case UPPER:
                return value.toUpperCase();
            default:
                return value;
        }
    }

}
