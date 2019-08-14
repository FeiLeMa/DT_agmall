package com.alag.agmall.business.module.order.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.api.model.OrderItem;
import com.alag.agmall.business.module.order.api.model.PayInfo;

import java.util.List;

public interface OrderService {

    ServerResponse<Boolean> getOrderStatusByOrderNoAndUserId(Integer userId, Long orderNo);

    ServerResponse createOrder(Integer id, Integer shippingId);

    ServerResponse canncelOrder(Integer userId, Long orderNo);

    ServerResponse getCartProduct(Integer userId);

    ServerResponse getDetail(Integer id, Long orderNo);

    ServerResponse list(Integer id, Integer pageNum, Integer pageSize);

    void closeOrder(int hour);

    ServerResponse<Order> selectOrderByOrderNoAndUserId(Long orderNo, Integer userId);

    ServerResponse<List<OrderItem>> selectOrderItemByOrderNoAndUserId(Long orderNo, Integer userId);

    ServerResponse<Order> selectOrderByOrderNo(Long orderNo);

    ServerResponse<Integer> updateOrder(Order order);

    ServerResponse<Integer> insertPayInfo(PayInfo payInfo);

    ServerResponse<PayInfo> selectPayInfoByOrderNo(Long orderNo);

    ServerResponse modifyOrderStatusAndAddPayInfo(Long orderNo);

    ServerResponse addPayInfo(AlipayInfo alipayInfo);
}
