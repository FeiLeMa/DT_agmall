package com.alag.agmall.business.module.shipping.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = {"com.alag.agmall.business.core.config",
                                            "com.alag.agmall.business.module.shipping.server"})
@EnableRedisHttpSession
@EnableEurekaClient
public class ShippingServer {
    public static void main(String[] args) {
        SpringApplication.run(ShippingServer.class, args);
    }
}
