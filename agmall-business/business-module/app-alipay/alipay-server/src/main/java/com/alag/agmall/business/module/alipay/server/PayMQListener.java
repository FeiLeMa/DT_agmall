package com.alag.agmall.business.module.alipay.server;

import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.order.feign.controller.OrderFeignController;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-18 23:54
 * @email: alag256@aliyun.com
 **/
@Component
@Slf4j
public class PayMQListener {

    @Autowired
    private OrderFeignController orderFeignController;


    @JmsListener(destination = "PAYINFO_NOTIFY")
    public void onMessage(String orderData) {
        AlipayInfo alipayInfo = JSONObject.parseObject(orderData, AlipayInfo.class);
        orderFeignController.addPayInfoByAPayInfo(alipayInfo);
    }
}
