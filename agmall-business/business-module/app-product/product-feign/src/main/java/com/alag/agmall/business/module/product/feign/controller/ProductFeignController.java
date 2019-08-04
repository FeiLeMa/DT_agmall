package com.alag.agmall.business.module.product.feign.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient("app-product")
public interface ProductFeignController {
}
