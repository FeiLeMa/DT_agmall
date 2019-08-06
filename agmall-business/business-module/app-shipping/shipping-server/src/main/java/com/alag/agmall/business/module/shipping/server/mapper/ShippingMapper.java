package com.alag.agmall.business.module.shipping.server.mapper;

import com.alag.agmall.business.module.shipping.api.model.Shipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShippingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int insert(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int insertSelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    Shipping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int updateByPrimaryKeySelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_shipping
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int updateByPrimaryKey(Shipping record);

    int deleteByUserIdAndId(@Param("userId") Integer userId, @Param("id") Integer id);

    Shipping selectByIdAndUserId(@Param("userId") Integer userId, @Param("id") Integer id);

    List<Shipping> selectByUserId(Integer userId);
}