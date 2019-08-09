package com.alag.agmall.business.module.message.server.service.impl;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.ParamBean;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.alag.agmall.business.module.message.server.exceptions.MessageBizException;
import com.alag.agmall.business.module.message.server.mapper.TransactionMessageMapper;
import com.alag.agmall.business.module.message.server.service.TMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TMessageServiceImpl implements TMessageService {
    @Autowired
    private TransactionMessageMapper transactionMessageMapper;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public ServerResponse<Integer> saveMessage(TransactionMessage message) {
        message.setStatus(Const.MessageEnum.WAITING_CONFIRM.getCode());
        message.setAreadlyDead(Const.TMessage.AREADLY_DEAD_NO);
        message.setMessageSendTimes(Const.TMessage.RE_SENT_TIMES_ZEROTH);
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());

        return ServerResponse.createBySuccess(transactionMessageMapper.insertSelective(message));
    }

    @Override
    public void confirmAndSendMsg(String messageId) {
        final TransactionMessage message = getMsgByMsgId(messageId).getData();
        if (message == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "根据消息id查找的消息为空");
        }
        message.setStatus(Const.MessageEnum.SENDING.getCode());
        message.setUpdateTime(new Date());
        transactionMessageMapper.updateByPrimaryKeySelective(message);

        jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
        jmsTemplate.send(session -> session.createTextMessage(message.getMessageBody()));
    }

    @Override
    public ServerResponse<Integer> saveAndSendMsg(TransactionMessage message) {
        message.setStatus(Const.MessageEnum.SENDING.getCode());
        message.setAreadlyDead(Const.TMessage.AREADLY_DEAD_NO);
        message.setMessageSendTimes(Const.TMessage.RE_SENT_TIMES_ZEROTH);
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());

        int row = transactionMessageMapper.insertSelective(message);

        jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
        jmsTemplate.send(session -> session.createTextMessage(message.getMessageBody()));

        return ServerResponse.createBySuccess(row);
    }

    @Override
    public void directMsg(TransactionMessage message) {
        jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
        jmsTemplate.send(session -> session.createTextMessage(message.getMessageBody()));
    }

    @Override
    public void reSendMsg(TransactionMessage message) {
        message.addSendTimes();
        message.setUpdateTime(new Date());
        transactionMessageMapper.updateByPrimaryKeySelective(message);

        jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
        jmsTemplate.send(session -> session.createTextMessage(message.getMessageBody()));
    }

    @Override
    public void rSendMsgByMsgId(String messageId) {
        final TransactionMessage message = getMsgByMsgId(messageId).getData();
        if (message == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "根据消息id查找的消息为空");
        }

        if (message.getMessageSendTimes() >= Const.TMessage.MESSAGE_MAX_SEND_TIMES) {
            message.setAreadlyDead(Const.TMessage.AREADLY_DEAD_YES);
        }

        message.setUpdateTime(new Date());
        message.addSendTimes();
        transactionMessageMapper.updateByPrimaryKeySelective(message);

        jmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
        jmsTemplate.send(session -> session.createTextMessage(message.getMessageBody()));

    }

    @Override
    public void setMsgToDead(String messageId) {
        final TransactionMessage message = getMsgByMsgId(messageId).getData();
        if (message == null) {
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL, "根据消息id查找的消息为空");
        }

        message.setUpdateTime(new Date());
        message.setAreadlyDead(Const.TMessage.AREADLY_DEAD_YES);
        transactionMessageMapper.updateByPrimaryKeySelective(message);
    }


    @Override
    public ServerResponse<TransactionMessage> getMsgByMsgId(String messageId) {
        return ServerResponse.createBySuccess(transactionMessageMapper.getByMessageId(messageId));
    }

    @Override
    public void delMsgByMsgId(String messageId) {
        transactionMessageMapper.delByMsgId(messageId);
    }

    @Override
    public void rSendMsgByBatchSizeAndQName(int pageNum, int pageSize, String queueName) {
        PageHelper.startPage(pageNum, pageSize);
        List<TransactionMessage> transactionMessages = transactionMessageMapper.selectByQueueName(queueName);
        PageInfo pageInfo = new PageInfo(transactionMessages);


        transactionMessages = pageInfo.getList();
        transactionMessages.forEach(message -> {
            message.setUpdateTime(new Date());
            message.addSendTimes();
            transactionMessageMapper.updateByPrimaryKeySelective(message);

            jmsTemplate.setDefaultDestinationName(queueName);
            jmsTemplate.send(session -> session.createTextMessage(message.getMessageBody()));
        });

    }

    @Override
    public ServerResponse<PageInfo> listPage(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPageNum(), paramBean.getPageSize());
        List<TransactionMessage> transactionMessages = transactionMessageMapper.list(paramBean);
        PageInfo pageInfo = new PageInfo(transactionMessages);

        return ServerResponse.createBySuccess(pageInfo);
    }
}
