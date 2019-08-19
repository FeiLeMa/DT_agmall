package com.alag.agmall.business.module.notify.feign;


import com.alag.agmall.business.module.notify.api.NotifyController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient("app-notify")
public interface NotifyFeignClient extends NotifyController {
}
