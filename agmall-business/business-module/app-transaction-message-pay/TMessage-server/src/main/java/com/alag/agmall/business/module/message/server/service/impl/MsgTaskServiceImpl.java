package com.alag.agmall.business.module.message.server.service.impl;

import com.alag.agmall.business.core.common.Const;
import com.alag.agmall.business.core.page.ParamBean;
import com.alag.agmall.business.core.util.DateTimeUtil;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;
import com.alag.agmall.business.module.alipay.feign.AlipayFeign;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.alag.agmall.business.module.message.server.mapper.TransactionMessageMapper;
import com.alag.agmall.business.module.message.server.service.MsgTaskService;
import com.alag.agmall.business.module.message.server.service.TMessageService;
import com.alag.agmall.business.module.order.api.model.Order;
import com.alag.agmall.business.module.order.feign.controller.OrderFeignController;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MsgTaskServiceImpl implements MsgTaskService {
    @Autowired
    private TransactionMessageMapper transactionMessageMapper;
    @Autowired
    private OrderFeignController orderFeign;
    @Autowired
    private TMessageService tMessageService;
    @Autowired
    private AlipayFeign alipayFeign;

    @Override
    public void handleWaitConfirmMessage() {
        ParamBean paramBean = ParamBean.newParamBean();
        Date createTimeBefore = this.getCreateTimeBefore();
        paramBean.set(pB->{
            pB.setAreadlyDead(Const.TMessage.AREADLY_DEAD_NO);
            pB.setListPageSortType("ASC");
            pB.setStatus(Const.MessageEnum.WAITING_CONFIRM.getCode());
            pB.setPageNum(1);
            pB.setPageSize(100);
            pB.setCreateTimeBefore(createTimeBefore);
        });

        PageHelper.startPage(paramBean.getPageNum(), paramBean.getPageSize());
        List<TransactionMessage> transactionMessages = transactionMessageMapper.list(paramBean);
        PageInfo pageInfo = new PageInfo(transactionMessages);

        List<TransactionMessage> transactionMessageList = pageInfo.getList();
        log.info("===================Waiting----Message查询结果============================");
        transactionMessageList.forEach(tMsg->{
            log.info("TransactionMessage->{}"+tMsg);
            log.info("================================================================");
            String msgID = tMsg.getMessageId();
            log.info("msgID{}已被取出",msgID);
            if (msgID.charAt(0) == 'O') {
                Long orderNo = Long.valueOf(msgID.replaceAll("Order", ""));
                log.info("当前TransactionMessage中的OrderNo为{}",orderNo);
                AlipayInfo alipayInfo = alipayFeign.getByOrderNo(orderNo).getData();
                if (alipayInfo == null) {
                    tMessageService.delMsgByMsgId(msgID);
                } else {
                    tMessageService.confirmAndSendMsg(msgID);
                }
            }else{
                Long orderNo = Long.valueOf(msgID.replaceAll("PayInfo", ""));
                Order order = orderFeign.queryOrderS(orderNo).getData();
                if (Const.OrderStatusEnum.PAID.getCode()==order.getStatus()) {
                    tMessageService.confirmAndSendMsg(msgID);
                } else {
                    tMessageService.delMsgByMsgId(msgID);
                }


            }
        });
    }








    @Override
    public void handleConfirmSendingMessage() {
        Map<Integer, Integer> timeoutMap = Maps.newHashMap();
        timeoutMap.put(0, Const.TMessage.FIRST_SEND_TIMES);
        timeoutMap.put(1, Const.TMessage.SECOND_SEND_TIMES);
        timeoutMap.put(2, Const.TMessage.THIRD_SEND_TIMES);
        timeoutMap.put(3, Const.TMessage.FOURTH_SEND_TIMES);
        timeoutMap.put(4, Const.TMessage.FIFTH_SEND_TIMES);

        ParamBean paramBean = ParamBean.newParamBean();
        Date createTimeBefore = this.getCreateTimeBefore();
        paramBean.set(pB->{
            pB.setAreadlyDead(Const.TMessage.AREADLY_DEAD_NO);
            pB.setListPageSortType("ASC");
            pB.setStatus(Const.MessageEnum.SENDING.getCode());
            pB.setPageNum(1);
            pB.setPageSize(100);
            pB.setCreateTimeBefore(createTimeBefore);
        });

        PageHelper.startPage(paramBean.getPageNum(), paramBean.getPageSize());
        List<TransactionMessage> transactionMessages = transactionMessageMapper.list(paramBean);
        PageInfo pageInfo = new PageInfo(transactionMessages);


        List<TransactionMessage> transactionMessageList = pageInfo.getList();
        log.info("===================ConfirmSending----Message查询结果============================");
        transactionMessageList.forEach(tMsg -> {
            log.info("TransactionMessage->{}" + tMsg);
            log.info("================================================================");
            int messageSendTimes = tMsg.getMessageSendTimes();
            if (messageSendTimes >= 5) {
                tMessageService.setMsgToDead(tMsg.getMessageId());
                return;
            }
            //本消息此次超时重发时间
            int currentMsgRSendTime = timeoutMap.get(messageSendTimes);
            Date hasSendTime = DateTimeUtil.addTime(tMsg.getUpdateTime(), currentMsgRSendTime);

            if (DateTimeUtil.before(new Date(),hasSendTime)) {
                log.info("这条消息应该在{}再次发送，当前时间{}", DateTimeUtil.dateToStr(hasSendTime), DateTimeUtil.dateToStr(new Date()));
                return;
            }

            tMessageService.reSendMsg(tMsg);
        });
    }

    private Date getCreateTimeBefore() {
        return DateTimeUtil.addTime(-Const.TMessage.MESSAGE_HANDLE_DURATION);
    }
}
