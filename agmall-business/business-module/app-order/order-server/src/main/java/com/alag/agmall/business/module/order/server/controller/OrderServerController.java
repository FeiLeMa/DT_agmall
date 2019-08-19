package com.alag.agmall.business.module.order.server.controller;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ResponseCode;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.api.model.OrderItem;
import com.alag.agmall.business.module.order.api.model.PayInfo;
import com.alag.agmall.business.module.order.server.service.OrderService;
import com.alag.agmall.business.module.user.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderServerController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    public ServerResponse create(HttpSession session,
                                 @RequestParam(value = "shippingId", required = true) Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后在操作");
        }
        return orderService.createOrder(user.getId(), shippingId);
    }

    @PutMapping("canncel")
    public ServerResponse canncel(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后在操作");
        }
        return orderService.canncelOrder(user.getId(), orderNo);
    }

    /**
     * 没有下单之前的购物车查询
     *
     * @param session
     * @return
     */
    @GetMapping("get_cart_product")
    public ServerResponse getCartProduct(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后在操作");
        }
        return orderService.getCartProduct(user.getId());

    }

    @GetMapping("detail")
    public ServerResponse getDetail(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后在操作");
        }
        return orderService.getDetail(user.getId(), orderNo);
    }

    @GetMapping("list")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后在操作");
        }
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @RequestMapping("query_order_pay_status")
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }

        ServerResponse response = orderService.getOrderStatusByOrderNoAndUserId(user.getId(), orderNo);
        if (response.isSuccess()) {
            return response;
        }
        return ServerResponse.createBySuccess(false);
    }

    @GetMapping("get_order_f")
    ServerResponse<Order> queryOrderF(@RequestParam("orderNo") Long orderNo,
                                     @RequestParam("userId") Integer userId) {
        return orderService.selectOrderByOrderNoAndUserId(orderNo, userId);
    }

    @GetMapping("get_order_item")
    ServerResponse<List<OrderItem>> queryOrderItem(@RequestParam("orderNo") Long orderNo,
                                                   @RequestParam("userId") Integer userId) {
        return orderService.selectOrderItemByOrderNoAndUserId(orderNo, userId);
    }

    @GetMapping("get_order_s")
    ServerResponse<Order> queryOrderS(@RequestParam("orderNo") Long orderNo) {
        return orderService.selectOrderByOrderNo(orderNo);
    }

    @PutMapping("modify_order")
    ServerResponse<Integer> modifyOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @PostMapping("add_pay_info")
    ServerResponse<Integer> addPayInfo(@RequestBody PayInfo payInfo) {
        return orderService.insertPayInfo(payInfo);
    }

    @GetMapping("get_pay_info")
    ServerResponse<PayInfo> getPayInfo(@RequestParam("orderNo") Long orderNo) {
        return orderService.selectPayInfoByOrderNo(orderNo);
    }

    @RequestMapping("add_pay_info_by_alipay_info")
    void addPayInfoByAPayInfo(@RequestBody AlipayInfo alipayInfo) {
        orderService.addPayInfo(alipayInfo);
    }
}
