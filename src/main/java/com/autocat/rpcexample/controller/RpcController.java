package com.autocat.rpcexample.controller;

import brave.Tracer;
import com.autocat.rpcexample.feign.ContextClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/rpc")
@RequiredArgsConstructor
@Slf4j
public class RpcController {

    private final ContextClient contextClient;
    private final Tracer tracer;
    private final Executor customtraceableExecutor;
    private final Executor threadPoolTaskExecutor;

    @GetMapping("/span")
    public String getSpanId(){
        log.info("getSpanId Start");
        log.info("now traceId = {}", tracer.currentSpan().context().traceIdString());
        return contextClient.getSpanId();
    }

    @GetMapping("/trace")
    public String getTraceId(){
        log.info("getTraceId Start");
        log.info("now spanId = {}", tracer.currentSpan().context().spanIdString());
        return contextClient.getTraceId();
    }

    @GetMapping("/async")
    public String asyncTest() {
        for (int i = 0; i < 500; i++) {
            CompletableFuture.runAsync(() -> {
                log.info("customtraceableExecutor thread {}", Thread.currentThread().getName());
            }, customtraceableExecutor);
        }
        for (int i = 0; i < 400; i++) {
            CompletableFuture.runAsync(() -> {
                log.debug("customtraceableExecutor thread {}", Thread.currentThread().getName());
            }, customtraceableExecutor);
        }

        for (int i = 0; i < 20; i++) {
            CompletableFuture.runAsync(() -> {
                log.warn("customtraceableExecutor thread {}", Thread.currentThread().getName());
            }, customtraceableExecutor);
        }
        for (int i = 0; i < 5; i++) {
            CompletableFuture.runAsync(() -> {
                log.info("customtraceableExecutor thread {}", Thread.currentThread().getName());
            }, customtraceableExecutor);
        }

        // Ensure all tasks are done
//        traceableExecutor.shutdown();
        log.info("All threads completed");

        return "Async operation started.";
    }
}
