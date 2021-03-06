package com.alag.agmall.business.module.cart.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = {"com.alag.agmall.business.core.config",
                                            "com.alag.agmall.business.module.cart.server"})
@EnableRedisHttpSession
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.alag.agmall.business.module.product.feign")
public class CartServer {
    public static void main(String[] args) {
        SpringApplication.run(CartServer.class, args);
    }
}
