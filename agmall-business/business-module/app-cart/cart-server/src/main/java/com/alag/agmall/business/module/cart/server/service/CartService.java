package com.alag.agmall.business.module.cart.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.CartVo;

public interface CartService {
    ServerResponse<CartVo> add(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer id, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer id, String productIds);

    ServerResponse<CartVo> list(Integer id);

    ServerResponse<CartVo> selectOrUnSelect(Integer id, Integer productId, int checked);

    ServerResponse<Integer> getProductCountInCart(Integer id);
}
