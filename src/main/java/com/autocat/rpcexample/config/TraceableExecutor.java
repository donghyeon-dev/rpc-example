package com.autocat.rpcexample.config;

import brave.Tracer;
import com.autocat.rpcexample.feign.ContextClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class TraceableExecutor {

    @Autowired
    BeanFactory beanFactory;
    @Bean
    public Executor customtraceableExecutor(){
        ExecutorService delegate = Executors.newFixedThreadPool(10);
        return new TraceableExecutorService(beanFactory, delegate);
    }

    @Bean
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        taskExecutor.setThreadNamePrefix("ThreadPoolTaskExecutor-");
        taskExecutor.initialize();

        return new TraceableExecutorService(beanFactory, taskExecutor.getThreadPoolExecutor());
    }

}
