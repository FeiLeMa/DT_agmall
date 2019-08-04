package com.alag.agmall.business.module.product.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ProductFeignApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductFeignApp.class, args);
    }
}
