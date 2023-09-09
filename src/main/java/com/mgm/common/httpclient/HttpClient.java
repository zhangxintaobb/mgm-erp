package com.mgm.common.httpclient;

import com.mgm.common.httpclient.response.RequestExecutor;

import java.util.concurrent.ExecutionException;

public interface HttpClient {
    <T> T getSync(String url, Class<T> responseType, long timeoutValueInSeconds) throws ExecutionException, InterruptedException;

    <T, Z> T putSync(String url, Z requestBody, Class<Z> requestType, Class<T> responseType, long timeoutValueInSeconds) throws ExecutionException, InterruptedException;

    <T, Z> T postSync(String url, Z requestBody, Class<Z> requestType, Class<T> responseType, long timeoutValueInSeconds) throws ExecutionException, InterruptedException;

    <T> RequestExecutor<T> getAsync(String url, Class<T> responseType, long timeoutValueInSeconds);

    <T, Z> RequestExecutor<T> postAsync(String url, Z requestBody, Class<Z> requestType, Class<T> responseType, long timeoutValueInSeconds);

    <T, Z> RequestExecutor<T> putAsync(String url, Z requestBody, Class<Z> requestType, Class<T> responseType, long timeoutValueInSeconds);
}
