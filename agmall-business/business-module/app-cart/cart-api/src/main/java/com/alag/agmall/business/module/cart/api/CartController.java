package com.alag.agmall.business.module.cart.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.CartVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public interface CartController {


    @PostMapping("add")
    ServerResponse<CartVo> add(Integer productId, Integer count);

    @PostMapping("update")
    ServerResponse<CartVo> update(Integer productId, Integer count);

    @DeleteMapping("delete")
    ServerResponse<CartVo> delete(String productIds);

    @GetMapping("list")
    ServerResponse<CartVo> list(HttpSession session);

    @PutMapping("select_all")
    ServerResponse<CartVo> selectAll(HttpSession session);

    @PutMapping("select")
    ServerResponse<CartVo> select(Integer productId);

    @PutMapping("un_select_all")
    ServerResponse<CartVo> unSelectAll(HttpSession session);

    @PutMapping("un_select")
    ServerResponse<CartVo> unSelect(Integer productId);

    @GetMapping("get_cart_product_count")
    ServerResponse<Integer> getCartCount(HttpSession session);
}
