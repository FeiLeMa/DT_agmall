package com.alag.agmall.business.module.user.feign.controller;

import com.alag.agmall.business.module.user.api.UserController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient(value = "app-user")
public interface UserFeignClientController extends UserController {
}
