package com.alag.agmall.business.module.cart.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.CartVo;
import com.alag.agmall.business.module.cart.api.model.Cart;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart/")
public interface CartController {


    @PostMapping("add")
    ServerResponse<CartVo> add(@RequestParam("productId") Integer productId,
                               @RequestParam("count")Integer count);

    @PostMapping("update")
    ServerResponse<CartVo> update(@RequestParam("productId")Integer productId,
                                  @RequestParam("count")Integer count);

    @DeleteMapping("delete")
    ServerResponse<CartVo> delete(@RequestParam("productIds")String productIds);

    @GetMapping("list")
    ServerResponse<CartVo> list();

    @PutMapping("select_all")
    ServerResponse<CartVo> selectAll();

    @PutMapping("select")
    ServerResponse<CartVo> select(@RequestParam("productId")Integer productId);

    @PutMapping("un_select_all")
    ServerResponse<CartVo> unSelectAll();

    @PutMapping("un_select")
    ServerResponse<CartVo> unSelect(@RequestParam("productId")Integer productId);

    @GetMapping("get_cart_product_count")
    ServerResponse<Integer> getCartCount();

    @GetMapping("get_cart")
    ServerResponse<List<Cart>> getCart(@RequestParam("userId") Integer userId);

    @GetMapping("get_checked_cart")
    ServerResponse<List<Cart>> getCheckedCart(@RequestParam("userId") Integer userId);

    @DeleteMapping("del")
    ServerResponse<Integer> delCart(@RequestParam("id")Integer id);

}
