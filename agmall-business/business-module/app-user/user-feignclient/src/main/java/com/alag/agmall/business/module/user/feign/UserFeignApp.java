package com.alag.agmall.business.module.user.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class UserFeignApp {
    public static void main(String[] args) {
        SpringApplication.run(UserFeignApp.class, args);
    }
}
