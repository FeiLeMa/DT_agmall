package com.alag.agmall.business.module.notify.server.mapper;

import com.alag.agmall.business.module.notify.api.model.NotifyRecordLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotifyRecordLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_notify_record_log
     *
     * @mbggenerated Sat Aug 17 15:02:22 CST 2019
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_notify_record_log
     *
     * @mbggenerated Sat Aug 17 15:02:22 CST 2019
     */
    int insert(NotifyRecordLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_notify_record_log
     *
     * @mbggenerated Sat Aug 17 15:02:22 CST 2019
     */
    int insertSelective(NotifyRecordLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_notify_record_log
     *
     * @mbggenerated Sat Aug 17 15:02:22 CST 2019
     */
    NotifyRecordLog selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_notify_record_log
     *
     * @mbggenerated Sat Aug 17 15:02:22 CST 2019
     */
    int updateByPrimaryKeySelective(NotifyRecordLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_notify_record_log
     *
     * @mbggenerated Sat Aug 17 15:02:22 CST 2019
     */
    int updateByPrimaryKey(NotifyRecordLog record);
}