package com.alag.agmall.business.module.cart.server.service.impl;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ResponseCode;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.util.BigDecimalUtil;
import com.alag.agmall.business.core.util.PropertiesUtil;
import com.alag.agmall.business.core.vo.CartProductVo;
import com.alag.agmall.business.core.vo.CartVo;
import com.alag.agmall.business.module.cart.api.model.Cart;
import com.alag.agmall.business.module.cart.server.mapper.CartMapper;
import com.alag.agmall.business.module.cart.server.service.CartService;
import com.alag.agmall.business.module.product.api.model.Product;
import com.alag.agmall.business.module.product.feign.controller.ProductFeignClient;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "参数不可为空");
        }
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            cart = new Cart();
            cart.setChecked(Const.Cart.CHECKED);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cart.setUserId(userId);

            int rowCount = cartMapper.insert(cart);
            if (rowCount <= 0) {
                return ServerResponse.createByErrorMessage("添加到购物车失败");
            }
        } else {
            cart.setQuantity(cart.getQuantity() + count);
            int rowCount = cartMapper.updateByPrimaryKeySelective(cart);
            if (rowCount <= 0) {
                return ServerResponse.createByErrorMessage("更新该商品在购物车中的数量失败");
            }
        }
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "参数不可为空");
        }
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
            return ServerResponse.createBySuccess(this.getCartVo(userId));
        }
        return ServerResponse.createByErrorMessage("购物车中没有此商品");
    }

    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }
        cartMapper.deleteByProductIds(userId,productIdList);

        return ServerResponse.createBySuccess(this.getCartVo(userId));
    }

    public ServerResponse<CartVo> list(Integer userId) {
        return ServerResponse.createBySuccess(this.getCartVo(userId));
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,int checked) {
        cartMapper.checkedOrUnChecked(userId, productId,checked);
        return this.list(userId);
    }

    @Override
    public ServerResponse<Integer> getProductCountInCart(Integer userId) {
        if (userId == null) {
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.getCountByUserId(userId));
    }

    private CartVo getCartVo(Integer userId) {
        BigDecimal totalPrice = new BigDecimal("0");
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectAllCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        for (Cart cartItem : cartList) {
            CartProductVo cartProductVo = new CartProductVo();
            cartProductVo.setId(cartItem.getId());
            cartProductVo.setUserId(userId);
            cartProductVo.setProductId(cartItem.getProductId());

            Product product = productFeignClient.getPJPById(cartItem.getProductId()).getData();
            cartProductVo.setProductMainImage(product.getMainImage());
            cartProductVo.setProductName(product.getName());
            cartProductVo.setProductSubtitle(product.getSubtitle());
            cartProductVo.setProductStatus(product.getStatus());
            cartProductVo.setProductPrice(product.getPrice());
            cartProductVo.setProductStock(product.getStock());

            Integer limitQuantity = 0;
            if (product.getStock() >= cartItem.getQuantity()){
                limitQuantity = cartItem.getQuantity();
                cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
            }else {
                cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                limitQuantity = product.getStock();
                Cart updateCartProductCount = new Cart();
                updateCartProductCount.setId(cartItem.getId());
                updateCartProductCount.setQuantity(limitQuantity);
                cartMapper.updateByPrimaryKeySelective(updateCartProductCount);
            }

            cartProductVo.setQuantity(limitQuantity);
            cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
            cartProductVo.setProductChecked(cartItem.getChecked());
            if(cartProductVo.getProductChecked() == Const.Cart.CHECKED){
                totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
            }
            cartProductVoList.add(cartProductVo);
        }

        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://www.alanagou.com/"));
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setAllChecked(this.isCheckedByUserId(userId));
        return cartVo;
    }

    private Boolean isCheckedByUserId(Integer userId) {
        return cartMapper.isCheckedByUserId(userId) == 0;
    }
}
