package com.mgm.common.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

@Slf4j
public class OptionalHelper {


    private OptionalHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> void resetEmptyCollectionToOptionalEmpty(T original) {
        if (original == null) {
            return;
        }
        Class<?> clazz = original.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            ReflectionUtils.makeAccessible(field);
            Object value = ReflectionUtils.getField(field, original);
            if (value == null) {
                continue;
            }
            if (!(value instanceof Optional)) {
                continue;
            }
            var optionalValue = (Optional) value;
            if (optionalValue.isEmpty()) {
                continue;
            }
            Object realValue = optionalValue.get();
            if (!(realValue instanceof Collection)) {
                continue;
            }
            var listValue = (Collection) realValue;
            if (!listValue.isEmpty()) {
                continue;
            }
            try {
                ReflectionUtils.setField(field, original, Optional.empty());
            } catch (Exception ex) {
                log.error("setOptionalEmpty error", ex);
            }
        }
    }

}
