package com.alag.agmall.business.module.alipay.server.service.impl;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.util.BigDecimalUtil;
import com.alag.agmall.business.core.util.DateTimeUtil;
import com.alag.agmall.business.core.util.IDGenerator;
import com.alag.agmall.business.core.util.PropertiesUtil;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.alipay.server.Thread.MyTreadPool;
import com.alag.agmall.business.module.alipay.server.function.MerchantNotityHandler;
import com.alag.agmall.business.module.alipay.server.mapper.AlipayInfoMapper;
import com.alag.agmall.business.module.alipay.server.service.AlipayService;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.alag.agmall.business.module.message.feign.controller.TransactionMessageFeign;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.api.model.OrderItem;
import com.alag.agmall.business.module.order.feign.controller.OrderFeignController;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private AlipayInfoMapper alipayInfoMapper;


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
    @Transactional
    public ServerResponse aNotifyBack(Map<String, String> params) {

        final Long orderNo = Long.parseLong(params.get("out_trade_no"));
        final String tradeNo = params.get("trade_no");
        final String tradeStatus = params.get("trade_status");
        final String gmtPayment = params.get("gmt_payment");

        Order order = orderFeign.queryOrderS(orderNo).getData();
        if (order == null) {
            log.info("订单不存在-orderNo:{}",orderNo);
            return ServerResponse.createByErrorMessage("订单不存在");
        }

        if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
            log.info("订单已经支付支付成功-orderNo{}",orderNo);
            return ServerResponse.createByErrorMessage("重复通知，订单已经支付");
        }

        if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            TransactionMessage message = TransactionMessage.setReturn(msg -> {
                msg.setCreator(Const.TMessage.CREATOR_NAME);
                msg.setEditor(Const.TMessage.EDITOR_NAME);
                msg.setId(IDGenerator.tMIDBuilder());
                msg.setMessageId(Const.TMessage.ORDER_MSG_ID_PRE + orderNo);
                msg.setMessageBody(JSONObject.toJSONString(orderNo));
                msg.setCreateTime(new Date());
                msg.setUpdateTime(new Date());
                msg.setConsumerQueue(Const.TMessage.ORDER_QUEUE_NAME);
                msg.setVersion(1);
                msg.setMessageDataType(Const.TMessage.MESSAGE_DATA_TYPE);
                msg.setRemark(Const.TMessage.REMARK);
            });
            ServerResponse response = tMessageFeign.saveMessageWaitingConfirm(message);
            log.info("预消息存储结果{}",response.isSuccess());
            if (response.isSuccess()) {
                AlipayInfo alipayInfo = AlipayInfo.setReturn((aliPay) -> {
                    aliPay.setId(IDGenerator.alipayInfoIDBuilder());
                    aliPay.setGmtPayment(DateTimeUtil.strToDate(gmtPayment));
                    aliPay.setOrderNo(orderNo);
                    aliPay.setTradeNo(tradeNo);
                    aliPay.setUserId(order.getUserId());
                    aliPay.setTradeStatus(tradeStatus);
                });
                int row = alipayInfoMapper.insert(alipayInfo);
                log.info("alipayInfo存储结果row{}",row);
                if (row > 0) {
                    tMessageFeign.confirmAndSendMessage(Const.TMessage.ORDER_MSG_ID_PRE + orderNo).queue();
                }
                log.info("支付成功发送商户通知");
                MerchantNotityHandler merchantNotityHandler = new MerchantNotityHandler(params, orderFeign, tMessageFeign);
                MyTreadPool.exector.submit(merchantNotityHandler);

                return ServerResponse.createBySuccess("预发送消息成功,添加alipayInfo成功，发送消息确认，发送商户通知线程开启");
            }
            return ServerResponse.createByErrorMessage("预发送消息失败");
        }
        return ServerResponse.createByErrorMessage("支付状态为Fail失败");
    }


    @Override
    public ServerResponse<AlipayInfo> selectByOrderNo(Long orderNo) {
        AlipayInfo alipayInfo = alipayInfoMapper.selectByOrderNo(orderNo);
        return ServerResponse.createBySuccess(alipayInfo);
    }
}
