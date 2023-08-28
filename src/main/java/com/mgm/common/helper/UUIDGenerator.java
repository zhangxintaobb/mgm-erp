package com.mgm.common.helper;

import java.util.UUID;

public class UUIDGenerator {
    public static String generate(String prefix) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        if (prefix != null && !prefix.isEmpty()) {
            uuidString = prefix + uuidString;
        }
        return uuidString;
    }
}
