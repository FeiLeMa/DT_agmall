package com.alag.agmall.business.module.order.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.api.model.OrderItem;
import com.alag.agmall.business.module.order.api.model.PayInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public interface OrderController {


    @PostMapping("create")
    ServerResponse create(
            @RequestParam(value = "shippingId", required = true) Integer shippingId);

    @PutMapping("canncel")
    ServerResponse canncel(@RequestParam("orderNo") Long orderNo);

    /**
     * 没有下单之前的购物车查询
     *
     * @param
     * @return
     */
    @GetMapping("get_cart_product")
    ServerResponse getCartProduct();

    @GetMapping("detail")
    ServerResponse getDetail(@RequestParam("orderNo")Long orderNo);

    @GetMapping("list")
    ServerResponse list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize);

    @RequestMapping("query_order_pay_status")
    ServerResponse<Boolean> queryOrderPayStatus(@RequestParam("orderNo") Long orderNo);

    //    ==============feign===========
    @GetMapping("get_order_f")
    ServerResponse<Order> queryOrderF(@RequestParam("orderNo") Long orderNo,
                                     @RequestParam("userId") Integer userId);

    @GetMapping("get_order_item")
    ServerResponse<List<OrderItem>> queryOrderItem(@RequestParam("orderNo") Long orderNo,
                                                   @RequestParam("userId") Integer userId);

    @GetMapping("get_order_s")
    ServerResponse<Order> queryOrderS(@RequestParam("orderNo") Long orderNo);

    @PutMapping("modify_order")
    ServerResponse<Integer> modifyOrder(@RequestBody Order order);

    @PostMapping("add_pay_info")
    ServerResponse<Integer> addPayInfo(@RequestBody PayInfo payInfo);

}
