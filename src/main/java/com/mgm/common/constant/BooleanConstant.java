package com.mgm.common.constant;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class BooleanConstant {
    @Getter
    @RequiredArgsConstructor
    public enum BooleanOperator {
        NOT("NOT"),
        AND("AND"),
        OR("OR"),
        LEFT_PA("\\("),
        RIGHT_PA("\\)");

        @NonNull
        private String value;
    }
}
