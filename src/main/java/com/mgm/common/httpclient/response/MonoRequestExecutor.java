package com.mgm.common.httpclient.response;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class MonoRequestExecutor<T> implements RequestExecutor<T> {
    Mono<T> response;

    public MonoRequestExecutor(Mono<T> response) {
        this.response = response;
    }

    // Choose one way below as needed

    // sync way
    @Override
    public T doBlock() throws NullPointerException {
        return this.response.block();
    }

    // async way, need to call this function or request is not sending out
    @Override
    public Disposable doSubscribe() throws NullPointerException {
        return this.response.subscribe();
    }

    @Override
    public void doOnSuccessOrError(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer) {
        response.subscribe(consumer, errorConsumer);
    }
}