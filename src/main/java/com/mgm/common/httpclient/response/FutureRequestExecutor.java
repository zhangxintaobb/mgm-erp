package com.mgm.common.httpclient.response;

import reactor.core.Disposable;
import reactor.util.annotation.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class FutureRequestExecutor<T> implements RequestExecutor<T> {
    CompletableFuture<T> response;

    public FutureRequestExecutor(CompletableFuture<T> response) {
        this.response = response;
    }

    @Override
    public T doBlock() throws InterruptedException, ExecutionException, NullPointerException {
        return response.get();
    }

    // not supported
    @Override
    public Disposable doSubscribe() throws NullPointerException {
       return null;
    }

    @Override
    public void doOnSuccessOrError(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer) {
        response.whenComplete((r, e) -> {
            if (consumer != null) {
                consumer.accept(r);
            }
            errorConsumer.accept(e);
        });
    }

}
