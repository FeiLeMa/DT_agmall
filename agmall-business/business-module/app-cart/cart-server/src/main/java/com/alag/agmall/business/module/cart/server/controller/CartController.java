package com.alag.agmall.business.module.cart.server.controller;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ResponseCode;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.CartVo;
import com.alag.agmall.business.module.cart.api.model.Cart;
import com.alag.agmall.business.module.cart.server.service.CartService;
import com.alag.agmall.business.module.user.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("add")
    public ServerResponse<CartVo> add(HttpSession session,
                                      @RequestParam("productId") Integer productId,
                                      @RequestParam("count") Integer count) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.add(currentUser.getId(), productId, count);
    }

    @PostMapping("update")
    public ServerResponse<CartVo> update(HttpSession session,
                                         @RequestParam("productId") Integer productId,
                                         @RequestParam("count") Integer count) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.update(currentUser.getId(), productId, count);
    }

    @DeleteMapping("delete")
    public ServerResponse<CartVo> delete(HttpSession session,
                                         @RequestParam("productIds") String productIds) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.deleteProduct(currentUser.getId(), productIds);
    }

    @GetMapping("list")
    public ServerResponse<CartVo> list(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.list(currentUser.getId());
    }

    @PutMapping("select_all")
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.selectOrUnSelect(currentUser.getId(), null, Const.Cart.CHECKED);
    }

    @PutMapping("select")
    public ServerResponse<CartVo> select(HttpSession session,
                                         @RequestParam("productId") Integer productId) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.selectOrUnSelect(currentUser.getId(), productId, Const.Cart.CHECKED);
    }

    @PutMapping("un_select_all")
    public ServerResponse<CartVo> unSelectAll(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.selectOrUnSelect(currentUser.getId(), null, Const.Cart.UN_CHECKED);
    }

    @PutMapping("un_select")
    public ServerResponse<CartVo> unSelect(HttpSession session,
                                           @RequestParam("productId") Integer productId) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "请先登录后再操作");
        }
        return cartService.selectOrUnSelect(currentUser.getId(), productId, Const.Cart.UN_CHECKED);
    }

    @GetMapping("get_cart_product_count")
    public ServerResponse<Integer> getCartCount(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createBySuccess(0);
        }
        return cartService.getProductCountInCart(currentUser.getId());
    }

//    ==============feign==============

    @GetMapping("get_cart")
    public ServerResponse<List<Cart>> getCart(@RequestParam("userId") Integer userId) {
        return cartService.getCartByUserId(userId);
    }

    @GetMapping("get_checked_cart")
    public ServerResponse<List<Cart>> getCheckedCart(@RequestParam("userId") Integer userId) {
        return cartService.getCheckCardByUserId(userId);
    }

    @DeleteMapping("del")
    public ServerResponse<Integer> delCart(Integer id) {
        return cartService.delById(id);
    }
}
