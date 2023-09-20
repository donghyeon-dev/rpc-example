package com.example.rpcexample.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient( name="CTX", url="localhost:8888")
public interface ContextClient {

    @GetMapping("/context/span-id")
    public String getSpanId();

    @GetMapping("/context/trace-id")
    public String getTraceId();
}
