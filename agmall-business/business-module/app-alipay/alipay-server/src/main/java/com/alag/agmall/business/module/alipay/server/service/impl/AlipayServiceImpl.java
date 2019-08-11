package com.alag.agmall.business.module.alipay.server.service.impl;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.util.BigDecimalUtil;
import com.alag.agmall.business.core.util.DateTimeUtil;
import com.alag.agmall.business.core.util.IDGenerator;
import com.alag.agmall.business.core.util.PropertiesUtil;
import com.alag.agmall.business.module.alipay.server.service.AlipayService;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.alag.agmall.business.module.message.feign.controller.TransactionMessageFeign;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.api.model.OrderItem;
import com.alag.agmall.business.module.order.api.model.PayInfo;
import com.alag.agmall.business.module.order.feign.controller.OrderFeignController;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private OrderFeignController orderFeign;
    @Autowired
    private TransactionMessageFeign tMessageFeign;

    @Override
    public ServerResponse<String> pay(Integer userId, Long orderNo) {
        Order order = orderFeign.queryOrderF(orderNo, userId).getData();
        if (null == order) {
            return ServerResponse.createByErrorMessage("该用户下没此订单");
        }
        List<OrderItem> orderItemList = orderFeign.queryOrderItem(orderNo, userId).getData();
        StringBuilder subject = new StringBuilder("");
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            String productName = orderItem.getProductName();
            Integer quantity = orderItem.getQuantity();
            BigDecimal totalPrice = orderItem.getTotalPrice();
            log.info(productName + ":" + totalPrice);
            subject.append(productName).append("X").append(quantity).append(" , ");
            totalAmount = BigDecimalUtil.add(totalAmount.doubleValue(), totalPrice.doubleValue());
        }
        log.info("共有商品,{}", subject);
        log.info("总计价格{},", totalAmount);
        AlipayClient alipayClient = new DefaultAlipayClient(
                PropertiesUtil.getProperty("alipay.url"),
                PropertiesUtil.getProperty("alipay.app_id"),
                PropertiesUtil.getProperty("alipay.app_private_key"),
                PropertiesUtil.getProperty("alipay.format"),
                PropertiesUtil.getProperty("alipay.charset"),
                PropertiesUtil.getProperty("alipay.alipay_public_key"),
                PropertiesUtil.getProperty("alipay.sign_type"));
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //回跳地址
        alipayRequest.setReturnUrl(PropertiesUtil.getProperty("alipay.return_url"));
        //通知地址
        alipayRequest.setNotifyUrl(PropertiesUtil.getProperty("alipay.notify_url"));
        //业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderNo + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + totalAmount + "," +
                "    \"subject\":\"" + subject + "\"" +
                "  }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
            log.info(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return ServerResponse.createBySuccess(form);
    }

    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        Order order = orderFeign.queryOrderS(orderNo).getData();
        if (order == null) {
            return ServerResponse.createByErrorMessage("agmmall的订单,回调忽略");
        }
        if (order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()) {
            return ServerResponse.createBySuccess("支付宝重复调用");
        }
        String msgId1 = IDGenerator.msgIDBuilder();
        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {

            order.setPaymentTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            TransactionMessage message = TransactionMessage.newTransactionMessage();
            message.set(msg -> {
                msg.setCreateTime(new Date());
                msg.setUpdateTime(new Date());
                msg.setConsumerQueue(Const.TMessage.CONSUMER_QUEUE_NAME);
                msg.setCreator(Const.TMessage.CREATOR_NAME);
                msg.setEditor(Const.TMessage.EDITOR_NAME);
                msg.setId(IDGenerator.tMIDBuilder());
                msg.setMessageId(msgId1);
                msg.setMessageBody("添加支付时间，修改订单状态");
                msg.setVersion(1);
                msg.setMessageDataType(Const.TMessage.MESSAGE_DATA_TYPE);
                msg.setRemark(Const.TMessage.REMARK);
            });
            ServerResponse response = tMessageFeign.saveMessageWaitingConfirm(message);
            if (response.isSuccess()) {
                tMessageFeign.confirmAndSendMessage(msgId1);
            } else {
                return ServerResponse.createByErrorMessage("预发送消息失败");
            }
//            orderFeign.modifyOrder(order);
            String msgId2 = IDGenerator.msgIDBuilder();
            PayInfo payInfo = new PayInfo();
            payInfo.setUserId(order.getUserId());
            payInfo.setOrderNo(order.getOrderNo());
            payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
            payInfo.setPlatformNumber(tradeNo);
            payInfo.setPlatformStatus(tradeStatus);

            tMessageFeign.saveMessageWaitingConfirm(TransactionMessage.newTransactionMessage(msg -> {
                msg.setId(IDGenerator.tMIDBuilder());
                msg.setMessageId(msgId2);
                msg.setMessageBody("添加支信息");
                msg.setCreateTime(new Date());
                msg.setUpdateTime(new Date());
                msg.setConsumerQueue(Const.TMessage.CONSUMER_QUEUE_NAME);
                msg.setCreator(Const.TMessage.CREATOR_NAME);
                msg.setEditor(Const.TMessage.EDITOR_NAME);
                msg.setVersion(1);
                msg.setMessageDataType(Const.TMessage.MESSAGE_DATA_TYPE);
                msg.setRemark(Const.TMessage.REMARK);
            }));
//        orderFeign.addPayInfo(payInfo);
            if (response.isSuccess()) {
                tMessageFeign.confirmAndSendMessage(msgId2);
            } else {
                return ServerResponse.createByErrorMessage("预发送消息失败");
            }
        } else {
            return ServerResponse.createByErrorMessage("交易未成功");
        }
        return ServerResponse.createBySuccess("预发送消息，确认消息成功！");
    }
}
