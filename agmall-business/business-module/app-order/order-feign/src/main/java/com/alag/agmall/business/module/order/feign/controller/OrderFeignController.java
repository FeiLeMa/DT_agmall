package com.alag.agmall.business.module.order.feign.controller;

import com.alag.agmall.business.module.order.api.OrderController;
import com.alag.agmall.business.module.order.feign.fallback.OrderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "app-order",fallbackFactory = OrderFallbackFactory.class)
@RestController
public interface OrderFeignController extends OrderController {
}
