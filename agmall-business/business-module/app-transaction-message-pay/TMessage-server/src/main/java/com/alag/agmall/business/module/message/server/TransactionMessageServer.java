package com.alag.agmall.business.module.message.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.alag.agmall.business.core.config",
        "com.alag.agmall.business.module.message.server"})
@EnableEurekaClient
@EnableScheduling
@EnableFeignClients(basePackages = {"com.alag.agmall.business.module.order.feign",
        "com.alag.agmall.business.module.alipay.feign"})
public class TransactionMessageServer {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMessageServer.class, args);
    }
}
