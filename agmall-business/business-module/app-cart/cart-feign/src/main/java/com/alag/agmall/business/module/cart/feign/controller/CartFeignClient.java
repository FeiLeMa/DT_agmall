package com.alag.agmall.business.module.cart.feign.controller;

import com.alag.agmall.business.module.cart.api.CartController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "app-cart")
@RestController
public interface CartFeignClient extends CartController {
}
