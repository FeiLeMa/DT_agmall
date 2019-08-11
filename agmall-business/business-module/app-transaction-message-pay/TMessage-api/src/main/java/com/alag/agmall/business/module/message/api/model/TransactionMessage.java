package com.alag.agmall.business.module.message.api.model;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Consumer;

@ToString
public class TransactionMessage implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.id
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.version
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private Integer version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.editor
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String editor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.creator
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.create_time
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.update_time
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.message_id
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String messageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.message_data_type
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String messageDataType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.consumer_queue
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String consumerQueue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.message_send_times
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private Integer messageSendTimes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.areadly_dead
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private Integer areadlyDead;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.status
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.remark
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.field1
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String field1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.field2
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String field2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.field3
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String field3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mmall_transaction_message.message_body
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    private String messageBody;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.id
     *
     * @return the value of mmall_transaction_message.id
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.id
     *
     * @param id the value for mmall_transaction_message.id
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.version
     *
     * @return the value of mmall_transaction_message.version
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.version
     *
     * @param version the value for mmall_transaction_message.version
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.editor
     *
     * @return the value of mmall_transaction_message.editor
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getEditor() {
        return editor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.editor
     *
     * @param editor the value for mmall_transaction_message.editor
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setEditor(String editor) {
        this.editor = editor == null ? null : editor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.creator
     *
     * @return the value of mmall_transaction_message.creator
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.creator
     *
     * @param creator the value for mmall_transaction_message.creator
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.create_time
     *
     * @return the value of mmall_transaction_message.create_time
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.create_time
     *
     * @param createTime the value for mmall_transaction_message.create_time
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.update_time
     *
     * @return the value of mmall_transaction_message.update_time
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.update_time
     *
     * @param updateTime the value for mmall_transaction_message.update_time
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.message_id
     *
     * @return the value of mmall_transaction_message.message_id
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.message_id
     *
     * @param messageId the value for mmall_transaction_message.message_id
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.message_data_type
     *
     * @return the value of mmall_transaction_message.message_data_type
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getMessageDataType() {
        return messageDataType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.message_data_type
     *
     * @param messageDataType the value for mmall_transaction_message.message_data_type
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setMessageDataType(String messageDataType) {
        this.messageDataType = messageDataType == null ? null : messageDataType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.consumer_queue
     *
     * @return the value of mmall_transaction_message.consumer_queue
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getConsumerQueue() {
        return consumerQueue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.consumer_queue
     *
     * @param consumerQueue the value for mmall_transaction_message.consumer_queue
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue == null ? null : consumerQueue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.message_send_times
     *
     * @return the value of mmall_transaction_message.message_send_times
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public Integer getMessageSendTimes() {
        return messageSendTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.message_send_times
     *
     * @param messageSendTimes the value for mmall_transaction_message.message_send_times
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setMessageSendTimes(Integer messageSendTimes) {
        this.messageSendTimes = messageSendTimes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.areadly_dead
     *
     * @return the value of mmall_transaction_message.areadly_dead
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public Integer getAreadlyDead() {
        return areadlyDead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.areadly_dead
     *
     * @param areadlyDead the value for mmall_transaction_message.areadly_dead
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setAreadlyDead(Integer areadlyDead) {
        this.areadlyDead = areadlyDead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.status
     *
     * @return the value of mmall_transaction_message.status
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.status
     *
     * @param status the value for mmall_transaction_message.status
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.remark
     *
     * @return the value of mmall_transaction_message.remark
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.remark
     *
     * @param remark the value for mmall_transaction_message.remark
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.field1
     *
     * @return the value of mmall_transaction_message.field1
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getField1() {
        return field1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.field1
     *
     * @param field1 the value for mmall_transaction_message.field1
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.field2
     *
     * @return the value of mmall_transaction_message.field2
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getField2() {
        return field2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.field2
     *
     * @param field2 the value for mmall_transaction_message.field2
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.field3
     *
     * @return the value of mmall_transaction_message.field3
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getField3() {
        return field3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.field3
     *
     * @param field3 the value for mmall_transaction_message.field3
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mmall_transaction_message.message_body
     *
     * @return the value of mmall_transaction_message.message_body
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mmall_transaction_message.message_body
     *
     * @param messageBody the value for mmall_transaction_message.message_body
     *
     * @mbggenerated Fri Aug 09 08:36:08 CST 2019
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody == null ? null : messageBody.trim();
    }

    public void addSendTimes() {
        messageSendTimes++;
    }

    public static TransactionMessage newTransactionMessage(Consumer<TransactionMessage> consumer) {
        TransactionMessage message = new TransactionMessage();
        consumer.accept(message);
        return message;
    }

    public static TransactionMessage newTransactionMessage() {
        return new TransactionMessage();
    }

    public void set(Consumer<TransactionMessage> consumer) {
        consumer.accept(this);
    }

}