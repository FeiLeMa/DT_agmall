package com.alag.agmall.business.module.category.feign.controller;

import com.alag.agmall.business.module.category.api.CategoryManageController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "app-category")
@RestController
public interface CategoryFeignController extends CategoryManageController {
}
