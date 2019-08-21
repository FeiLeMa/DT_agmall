package com.alag.agmall.business.module.maxnotify.server.listener;

import com.alag.agmall.business.module.maxnotify.server.delay.Message;
import com.alag.agmall.business.module.maxnotify.server.delay.Task;
import com.alag.agmall.business.module.maxnotify.server.init.InitNotifyParam;
import com.alag.agmall.business.module.maxnotify.server.thread.MyTreadPool;
import com.alag.agmall.business.module.notify.api.Const.NotifyConst;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alag.agmall.business.module.notify.feign.NotifyFeignClient;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-18 23:54
 * @email: alag256@aliyun.com
 **/
@Component
@Slf4j
public class NotifyMQListener {

    @Autowired
    private NotifyFeignClient notifyFeignClient;
    @Autowired
    private InitNotifyParam initNotifyParam;

    @JmsListener(destination = "MERCHANT_NOTIFY")
    public void onMessage(String orderData) {
        log.info("开始监听MERCHANT_NOTIFY队列！");
        NotifyRecord notifyRecord = JSONObject.parseObject(orderData, NotifyRecord.class);
        notifyRecord.setStatus(NotifyConst.NotifyStatusEnum.CREATED.getCode());
        notifyRecord.setCreateTime(new Date());
        notifyRecord.setUpdateTime(new Date());
        if (notifyRecord.getId() != null) {
            NotifyRecord notifyRecord1 = notifyFeignClient.getNotifyRecordById(notifyRecord.getId()).getData();
            if (notifyRecord1 != null) {
                return;
            }
        }
        notifyFeignClient.createNotifyRecord(notifyRecord);
        Task task = Task.getInstance(initNotifyParam,notifyFeignClient);
        task.getDelayQueue().offer(new Message(notifyRecord));
        MyTreadPool.exector.submit(task);
    }
}
