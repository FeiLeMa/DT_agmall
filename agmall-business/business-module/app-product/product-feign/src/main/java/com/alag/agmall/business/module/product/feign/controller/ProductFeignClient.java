package com.alag.agmall.business.module.product.feign.controller;

import com.alag.agmall.business.module.product.api.ProductController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient(value = "app-product")
public interface ProductFeignClient extends ProductController {
}
