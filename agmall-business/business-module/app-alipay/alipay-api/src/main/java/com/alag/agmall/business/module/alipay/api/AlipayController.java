package com.alag.agmall.business.module.alipay.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("alipay")
public interface AlipayController {

    @GetMapping("query_alipay_info")
    @ResponseBody
    ServerResponse<AlipayInfo> getByOrderNo(@RequestParam("orderNo") Long orderNo);

}
