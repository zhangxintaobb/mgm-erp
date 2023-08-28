package com.mgm.common.api;


// only worthy of info log, not an error log
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
