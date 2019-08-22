package com.alag.agmall.business.module.alipay.server.function;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.Map2UrlString;
import com.alag.agmall.business.core.util.PropertiesUtil;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.alag.agmall.business.module.message.feign.controller.TransactionMessageFeign;
import com.alag.agmall.business.module.notify.api.Const.IDGenerator;
import com.alag.agmall.business.module.notify.api.Const.NotifyConst;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.feign.controller.OrderFeignController;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-18 21:33
 * @email: alag256@aliyun.com
 **/
@Slf4j
public class MerchantNotityHandler implements Runnable {
    private Map<String, String> paramsMap = null;
    private OrderFeignController orderFeign;
    private TransactionMessageFeign tMessageFeign;

    public MerchantNotityHandler(Map<String, String> paramsMap, OrderFeignController orderFeign, TransactionMessageFeign tMessageFeign) {
        this.paramsMap = paramsMap;
        this.orderFeign = orderFeign;
        this.tMessageFeign = tMessageFeign;
    }

    @Override
    public void run() {
        log.info("已经进入MerchantNotityHandler");
        final Long orderNo = Long.parseLong(paramsMap.get("out_trade_no"));
        final String tradeStatus = paramsMap.get("trade_status");
        final String gmtPayment = paramsMap.get("gmt_payment");

        Order order = orderFeign.queryOrderS(orderNo).getData();
        if (null == order) {
            log.info("未查到订单orderNo:{}",orderNo);
            return;
        }

        BigDecimal payment = order.getPayment();
        Integer userId = order.getUserId();
        Date createTime = order.getCreateTime();

        Map<String, Object> urlMap = Maps.newHashMap();
        urlMap.put("payment", payment);
        urlMap.put("paymentTime", gmtPayment);
        urlMap.put("userId", userId);
        urlMap.put("createTime", createTime);
        urlMap.put("status", tradeStatus);
        String returnUrl = Map2UrlString.work(urlMap);
        String orderNoStr = orderNo.toString();

        String sendMerchantText = this.getSendRrecordStr(returnUrl, orderNoStr, PropertiesUtil.getProperty(Const.Property.MERCHANT_NO));
        TransactionMessage tMsg = TransactionMessage.setReturn(tm -> {
            tm.setConsumerQueue(Const.TMessage.MERCHANT_QUEUE_NAME);
            tm.setMessageBody(sendMerchantText);
        });
        tMessageFeign.directSendMessage(tMsg);
        log.info("商户通知已经投递到MQ");
    }

    private String getSendRrecordStr(String returnUrl, String orderNo, String merchantNo) {
        Map<String, String> notifyRule = Maps.newHashMap();
        notifyRule.put("1", "0");
        notifyRule.put("2", "2");
        notifyRule.put("3", "3");
        notifyRule.put("4", "4");
        notifyRule.put("5", "8");
        String notifyRuleStr = JSONObject.toJSONString(notifyRule);
        NotifyRecord notifyRecord = NotifyRecord.setReturn(nRecord -> {
            nRecord.setId(IDGenerator.notifyRecordIDBuilder());
            nRecord.setLimitNotifyTimes(5);
            nRecord.setMerchantNo(merchantNo);
            nRecord.setMerchantOrderNo(orderNo);
            nRecord.setNotifyTimes(0);
            nRecord.setUrl(returnUrl);
            nRecord.setNotifyType(NotifyConst.NotifyTypeEnum.MERCHANT.getCode());
            nRecord.setNotifyRule(notifyRuleStr);
            return nRecord;
        });
        return JSONObject.toJSONString(notifyRecord);
    }
}
