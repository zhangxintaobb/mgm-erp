package com.mgm.common.httpclient.response;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface RequestExecutor<T> {
    T doBlock() throws InterruptedException, ExecutionException, NullPointerException;

    Disposable doSubscribe() throws NullPointerException;

    /**
     * In Async request, We can not nest Sync request by block()
     *
     * @param consumer
     * @param errorConsumer
     * @see <a href="https://github.com/reactor/reactor-core/releases/tag/v3.2.0.M2">blocking within a reactive pipeline throws an error</a>
     */
    void doOnSuccessOrError(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer);
}
