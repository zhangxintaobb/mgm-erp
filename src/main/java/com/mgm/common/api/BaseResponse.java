package com.mgm.common.api;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

@Value
public class BaseResponse<T> {
    @NonNull T data;

    public static ObjectNode processingResponse = JsonNodeFactory.instance.objectNode().put("message", "processing");
    public static ObjectNode refreshingResponse = JsonNodeFactory.instance.objectNode().put("message", "refreshing");
    public static ObjectNode successfulResponse = JsonNodeFactory.instance.objectNode().put("message", "successful");
}
