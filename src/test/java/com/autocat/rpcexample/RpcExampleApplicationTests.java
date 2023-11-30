package com.autocat.rpcexample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
class RpcExampleApplicationTests {

    @Test
    void asynctest() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            CompletableFuture.runAsync(() -> {
                log.info("Executing thread {}", Thread.currentThread().getName());
            }, executor);
        }

        // Ensure all tasks are done
        executor.shutdown();

        log.info("All threads completed");
    }
}