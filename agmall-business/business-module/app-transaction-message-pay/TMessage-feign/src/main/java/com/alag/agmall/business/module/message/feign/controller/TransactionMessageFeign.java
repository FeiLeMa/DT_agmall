package com.alag.agmall.business.module.message.feign.controller;

import com.alag.agmall.business.module.message.api.TransactionMessageController;
import com.alag.agmall.business.module.message.feign.fallback.MyFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient(value = "app-message",fallbackFactory = MyFallbackFactory.class)
public interface TransactionMessageFeign extends TransactionMessageController {
}
