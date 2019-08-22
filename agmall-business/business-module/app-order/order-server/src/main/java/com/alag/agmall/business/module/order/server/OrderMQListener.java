package com.alag.agmall.business.module.order.server;

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


    @JmsListener(destination= "ORDER_NOTIFY")
    public void onMessage(String orderData) {
            Long orderNo = JSONObject.parseObject(orderData, Long.class);
            orderService.modifyOrderStatusAndAddPayInfo(orderNo);
    }
}
