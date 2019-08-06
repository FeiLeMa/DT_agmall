package com.alag.agmall.business.module.cart.server.mapper;

import com.alag.agmall.business.module.cart.api.model.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int insertSelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int updateByPrimaryKeySelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Cart> selectAllCartByUserId(Integer userId);

    int isCheckedByUserId(Integer userId);

    int deleteByProductIds(@Param("userId") Integer userId, @Param("productIds") List<String> productIdList);

    int checkedOrUnChecked(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") int checked);

    int getCountByUserId(Integer userId);

    List<Cart> selectCheckedCardByUserId(Integer userId);
}