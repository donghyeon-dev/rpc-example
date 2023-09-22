package com.example.rpcexample.controller;

import brave.Tracer;
import com.example.rpcexample.feign.ContextClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
@RequiredArgsConstructor
@Slf4j
public class RpcController {

    private final ContextClient contextClient;
    private final Tracer tracer;

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
}
