package com.alag.agmall.business.module.order.api;

import com.alag.agmall.business.core.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public interface OrderController {


    @PostMapping("create")
    ServerResponse create(
            @RequestParam(value = "shippingId", required = true) Integer shippingId);

    @PutMapping("canncel")
    ServerResponse canncel(Long orderNo);

    /**
     * 没有下单之前的购物车查询
     *
     * @param
     * @return
     */
    @GetMapping("get_cart_product")
    ServerResponse getCartProduct();

    @GetMapping("detail")
    ServerResponse getDetail(Long orderNo);

    @GetMapping("list")
    ServerResponse list(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize);

    @RequestMapping("query_order_pay_status")
    ServerResponse<Boolean> queryOrderPayStatus(@RequestParam("orderNo") Long orderNo);

}
