package com.alag.agmall.business.module.alipay.feign;

import com.alag.agmall.business.module.alipay.api.AlipayController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;

@Controller
@FeignClient("app-alipay")
public interface AlipayFeign extends AlipayController {
}
