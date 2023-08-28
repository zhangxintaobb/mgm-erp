package com.mgm.common.collection;

public class Optional {
    public static <T> T unwrap(java.util.Optional<T> wrapped) {
        return wrapped.orElse(null);
    }
    public static <T> Boolean isPresent(T wrapped) {
        return java.util.Optional.ofNullable(wrapped).isPresent();
    }
}
