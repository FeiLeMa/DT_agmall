package com.alag.agmall.business.module.order.server.service;

import com.alag.agmall.business.core.common.ServerResponse;

public interface OrderService {

    ServerResponse<Boolean> getOrderStatusByOrderNoAndUserId(Integer userId, Long orderNo);

    ServerResponse createOrder(Integer id, Integer shippingId);

    ServerResponse canncelOrder(Integer userId, Long orderNo);

    ServerResponse getCartProduct(Integer userId);

    ServerResponse getDetail(Integer id, Long orderNo);

    ServerResponse list(Integer id, Integer pageNum, Integer pageSize);

    void closeOrder(int hour);
}
