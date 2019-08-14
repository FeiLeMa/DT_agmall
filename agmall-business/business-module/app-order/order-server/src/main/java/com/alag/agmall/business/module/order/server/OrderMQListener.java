package com.alag.agmall.business.module.order.server;

import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.order.server.service.OrderService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMQListener {
    @Autowired
    private OrderService orderService;


    @JmsListener(destination= "*")
    public void onMessage(String orderData) {
        if (orderData.length() == 18) {
            Long orderNo = JSONObject.parseObject(orderData, Long.class);
            orderService.modifyOrderStatusAndAddPayInfo(orderNo);
        } else {
            AlipayInfo alipayInfo = JSONObject.parseObject(orderData, AlipayInfo.class);
            orderService.addPayInfo(alipayInfo);
        }
    }
}
