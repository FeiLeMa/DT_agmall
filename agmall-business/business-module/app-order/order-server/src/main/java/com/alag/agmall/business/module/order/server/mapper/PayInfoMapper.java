package com.alag.agmall.business.module.order.server.mapper;

import com.alag.agmall.business.module.order.api.model.PayInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int insert(PayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int insertSelective(PayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    PayInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int updateByPrimaryKeySelective(PayInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_pay_info
     *
     * @mbggenerated Mon Jul 01 16:02:53 CST 2019
     */
    int updateByPrimaryKey(PayInfo record);
}