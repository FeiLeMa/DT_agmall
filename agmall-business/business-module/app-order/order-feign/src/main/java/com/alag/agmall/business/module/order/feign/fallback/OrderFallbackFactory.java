package com.alag.agmall.business.module.order.feign.fallback;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.api.model.OrderItem;
import com.alag.agmall.business.module.order.api.model.PayInfo;
import com.alag.agmall.business.module.order.feign.controller.OrderFeignController;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFallbackFactory implements FallbackFactory<OrderFeignController> {
    @Override
    public OrderFeignController create(Throwable throwable) {
        return new OrderFeignController() {
            @Override
            public ServerResponse create(Integer shippingId) {
                return ServerResponse.createByErrorMessage("");
            }

            @Override
            public ServerResponse canncel(Long orderNo) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse getCartProduct() {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse getDetail(Long orderNo) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse list(Integer pageNum, Integer pageSize) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse<Boolean> queryOrderPayStatus(Long orderNo) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse<Order> queryOrderF(Long orderNo, Integer userId) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse<List<OrderItem>> queryOrderItem(Long orderNo, Integer userId) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse<Order> queryOrderS(Long orderNo) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse<Integer> modifyOrder(Order order) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse<Integer> addPayInfo(PayInfo payInfo) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public ServerResponse<PayInfo> getPayInfo(Long orderNo) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public void addPayInfoByAPayInfo(AlipayInfo alipayInfo) {

            }
        };
    }
}
