package com.alag.agmall.business.module.productback.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.alag.agmall.business.core.config",
        "com.alag.agmall.business.module.productback.server"},
        exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.alag.agmall.business.module.product.feign")
public class ProductbackServer {
    public static void main(String[] args) {
        SpringApplication.run(ProductbackServer.class, args);
    }
}
