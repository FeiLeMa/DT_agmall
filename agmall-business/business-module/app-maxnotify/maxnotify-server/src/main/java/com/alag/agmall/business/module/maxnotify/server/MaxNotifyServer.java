package com.alag.agmall.business.module.maxnotify.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.alag.agmall.business.module.notify.feign")
public class MaxNotifyServer {
    public static void main(String[] args) {
        SpringApplication.run(MaxNotifyServer.class, args);
    }
}
