package com.mgm.common.api;

import lombok.*;

@Value
public class ErrorResponseBody {
    @NonNull String message;
}
