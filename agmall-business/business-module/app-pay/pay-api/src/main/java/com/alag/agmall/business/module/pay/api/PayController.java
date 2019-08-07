package com.alag.agmall.business.module.pay.api;


import com.alag.agmall.business.core.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("pay")
public interface PayController {

    @RequestMapping("alipay")
    String pay(@RequestParam(value = "orderNo", required = true) Long orderNo);

    @RequestMapping("alipay_callback")
    @ResponseBody
    ServerResponse<Map<String, String>> alipayCallback();

    @RequestMapping("alipay_notify")
    @ResponseBody
    Object alipayNotify();


    @RequestMapping("query_order_pay_status")
    @ResponseBody
    ServerResponse<Boolean> queryOrderPayStatus(Long orderNo);
}
