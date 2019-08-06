package com.alag.agmall.business.module.productback.feign.controller;


import com.alag.agmall.business.module.productback.api.ProductManageController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient(value = "app-productback")
public interface ProductbackFeignClient extends ProductManageController {
}
