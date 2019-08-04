package com.alag.agmall.business.module.category.feign.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient("app-category")
public interface CategoryFeignController {
}
