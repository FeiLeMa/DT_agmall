package com.alag.agmall.business.module.message.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.ParamBean;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.github.pagehelper.PageInfo;

public interface TMessageService {
    ServerResponse<Integer> saveMessage(TransactionMessage message);

    ServerResponse confirmAndSendMsg(String messageId);

    ServerResponse<Integer> saveAndSendMsg(TransactionMessage message);

    void directMsg(TransactionMessage message);

    void reSendMsg(TransactionMessage message);

    void rSendMsgByMsgId(String messageId);

    void setMsgToDead(String messageId);

    ServerResponse<TransactionMessage> getMsgByMsgId(String messageId);

    void delMsgByMsgId(String messageId);

    void rSendMsgByBatchSizeAndQName(int pageNum, int pageSize, String queueName);

    ServerResponse<PageInfo> listPage(ParamBean paramBean);
}
