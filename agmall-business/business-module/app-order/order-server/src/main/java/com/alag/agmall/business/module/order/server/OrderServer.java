package com.alag.agmall.business.module.order.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = {"com.alag.agmall.business.core.config",
        "com.alag.agmall.business.module.order.server"})
@EnableRedisHttpSession
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.alag.agmall.business.module.cart.feign",
        "com.alag.agmall.business.module.shipping.feign",
        "com.alag.agmall.business.module.product.feign",
        "com.alag.agmall.business.module.message.feign",
        "com.alag.agmall.business.module.alipay.feign"
})
@EnableJms
public class OrderServer {
    public static void main(String[] args) {
        SpringApplication.run(OrderServer.class, args);
    }
}
