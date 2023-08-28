package com.mgm.common.api;

public class ForbiddenRuntimeException extends RuntimeException {
    public ForbiddenRuntimeException(String message) {
        super(message);
    }
}
