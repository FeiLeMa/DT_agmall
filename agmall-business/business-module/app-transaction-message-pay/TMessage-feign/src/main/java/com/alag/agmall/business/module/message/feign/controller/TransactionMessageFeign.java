package com.alag.agmall.business.module.message.feign.controller;

import com.alag.agmall.business.module.message.api.TransactionMessageController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient("app-message")
public interface TransactionMessageFeign extends TransactionMessageController {
}
