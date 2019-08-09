package com.alag.agmall.business.module.message.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.alag.agmall.business.core.config",
        "com.alag.agmall.business.module.message.server"})
@EnableEurekaClient
public class TransactionMessageServer {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMessageServer.class, args);
    }
}
