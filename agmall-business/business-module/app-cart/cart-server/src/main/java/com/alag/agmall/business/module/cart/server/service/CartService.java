package com.alag.agmall.business.module.cart.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.CartVo;
import com.alag.agmall.business.module.cart.api.model.Cart;

import java.util.List;


public interface CartService {
    ServerResponse<CartVo> add(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer id, String productIds);

    ServerResponse<CartVo> list(Integer id);

    ServerResponse<CartVo> selectOrUnSelect(Integer id, Integer productId, int checked);

    ServerResponse<Integer> getProductCountInCart(Integer id);

    ServerResponse<List<Cart>> getCartByUserId(Integer userId);

    ServerResponse<List<Cart>> getCheckCardByUserId(Integer userId);

    ServerResponse<Integer> delById(Integer id);
}
