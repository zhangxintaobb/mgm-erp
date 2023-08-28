package com.mgm.common.constant;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class GlobalConstant {

    @Getter
    @RequiredArgsConstructor
    public enum Order {
        ASC("asc"),
        DESC("desc");

        @NonNull
        private String type;
    }

    public enum OrderUpperCase {
        ASC, DESC;
    }
}
