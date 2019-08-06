package com.alag.agmall.business.module.shipping.feign.controller;

import com.alag.agmall.business.module.shipping.api.ShippingController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient("app-shipping")
public interface ShippingFeignClient extends ShippingController {
}
