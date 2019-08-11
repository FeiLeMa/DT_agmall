package com.alag.agmall.business.module.alipay.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableRedisHttpSession
@EnableFeignClients(basePackages = {"com.alag.agmall.business.module.order.feign",
        "com.alag.agmall.business.module.message.feign"})
public class AlipayServer {
    public static void main(String[] args) {
        SpringApplication.run(AlipayServer.class, args);
    }
}
