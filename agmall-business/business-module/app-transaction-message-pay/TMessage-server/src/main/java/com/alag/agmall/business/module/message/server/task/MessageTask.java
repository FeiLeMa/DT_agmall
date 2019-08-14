package com.alag.agmall.business.module.message.server.task;


import com.alag.agmall.business.module.message.server.service.MsgTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageTask {
    private int w_fixedRateCount = 1;
    private int s_fixedRateCount = 1;
    @Autowired
    private MsgTaskService msgTaskService;

    @Scheduled(fixedRate = 30*1000)
    public void handleWaitingConfirmMsg() {
//        处理等待确认超时的消息
        log.info("第{}次执行handleWaitingConfirmMsg方法",w_fixedRateCount++);
        log.info("执行(处理[waiting_confirm]状态的消息)任务开始");
        msgTaskService.handleWaitConfirmMessage();
        log.info("执行(处理[waiting_confirm]状态的消息)任务结束");


//        处理确认发送中，但没有被成功消费确认的消息
        log.info("第{}次执行handleConfirmSendingMessage方法",s_fixedRateCount++);
        log.info("执行(处理[confirm_sending]状态的消息)任务开始");
        msgTaskService.handleConfirmSendingMessage();
        log.info("执行(处理[confirm_sending]状态的消息)任务结束");
    }



}
