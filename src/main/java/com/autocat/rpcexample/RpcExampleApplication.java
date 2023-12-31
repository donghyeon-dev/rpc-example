package com.autocat.rpcexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RpcExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcExampleApplication.class, args);
    }

}
