package com.alag.agmall.business.module.maxnotify.server.delay;

import com.alag.agmall.business.core.util.httpclient.SimpleHttpParam;
import com.alag.agmall.business.core.util.httpclient.SimpleHttpResult;
import com.alag.agmall.business.core.util.httpclient.SimpleHttpUtils;
import com.alag.agmall.business.module.maxnotify.server.init.InitNotifyParam;
import com.alag.agmall.business.module.notify.api.Const.IDGenerator;
import com.alag.agmall.business.module.notify.api.Const.NotifyConst;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alag.agmall.business.module.notify.api.model.NotifyRecordLog;
import com.alag.agmall.business.module.notify.feign.NotifyFeignClient;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.DelayQueue;

@Slf4j
public class Task implements Runnable {
    private static Task task;

    private static DelayQueue<Message> queue = new DelayQueue<Message>();
    private InitNotifyParam notifyParam;
    private NotifyFeignClient notifyFeignClient;

    private Task(InitNotifyParam notifyParam, NotifyFeignClient notifyFeignClient) {
        this.notifyParam = notifyParam;
        this.notifyFeignClient = notifyFeignClient;
    }

    public DelayQueue<Message> getDelayQueue() {
        return queue;
    }

    public static Task getInstance(InitNotifyParam notifyParam, NotifyFeignClient notifyFeignClient) {
        if (task == null) {
            synchronized (Task.class) {
                if (task == null) {
                    task = new Task(notifyParam, notifyFeignClient);
                }
            }
        }
        return task;
    }

    @Override
    public void run() {
        while (true) {
            Date currentDate = new Date();

            try {
                Message msg = queue.take();
                NotifyRecord notifyRecord = msg.getMsgBody();

                Integer notifyTimes = notifyRecord.getNotifyTimes(); // 得到当前通知对象的通知次数
                Integer maxNotifyTimes = notifyRecord.getLimitNotifyTimes(); // 最大通知次数

                //执行通知
                SimpleHttpParam param = new SimpleHttpParam(notifyRecord.getUrl());
                SimpleHttpResult result = SimpleHttpUtils.httpRequest(param);

                notifyRecord.setUpdateTime(currentDate); // 取本次通知时间作为最后修改时间
                notifyRecord.setNotifyTimes(notifyTimes + 1); // 通知次数+1

                String successValue = notifyParam.getSuccessValue(); // 通知成功标识

                String responseMsg = "";
                Integer responseStatus = result.getStatusCode();

                //记录日志
                String finalResponseMsg = responseMsg;
                NotifyRecordLog recordLog = NotifyRecordLog.setReturn(nRLog -> {
                    nRLog.setCreateTime(currentDate);
                    nRLog.setHttpStatus(responseStatus.toString());
                    nRLog.setId(IDGenerator.notifyRecordIDBuilder());
                    nRLog.setMerchantNo(notifyRecord.getMerchantNo());
                    nRLog.setMerchantOrderNo(notifyRecord.getMerchantOrderNo());
                    nRLog.setNotifyId(notifyRecord.getId());
                    nRLog.setRequest(notifyRecord.getUrl());
                    nRLog.setResponse(finalResponseMsg);
                    nRLog.setVersion(1);
                    nRLog.setUpdateTime(currentDate);
                    return nRLog;
                });
                notifyFeignClient.createNotifyRecordLog(recordLog);

                if (responseStatus == 200 || responseStatus == 201 || responseStatus == 202 || responseStatus == 203
                        || responseStatus == 204 || responseStatus == 205 || responseStatus == 206) {

                    responseMsg = result.getContent().trim();
                    responseMsg = responseMsg.length() >= 600 ? responseMsg.substring(0, 600) : responseMsg; // 避免异常日志过长
                    log.info("当前订单{}，响应结果{}", notifyRecord.getMerchantOrderNo(), responseMsg);
                    // 通知成功,更新通知记录为已通知成功（以后不再通知）
                    if (responseMsg.trim().equals(successValue)) {
                        notifyRecord.setStatus(NotifyConst.NotifyStatusEnum.SUCCESS.getCode());
                    }else {
                        // 通知不成功（返回的结果不是success）
                        if (notifyRecord.getNotifyTimes() < maxNotifyTimes) {
                            // 判断是否超过重发次数，未超重发次数的，再次进入延迟发送队列
                            notifyRecord.setStatus(NotifyConst.NotifyStatusEnum.HTTP_REQUEST_FALIED.getCode());
                            queue.offer(new Message(notifyRecord));

                        } else {
                            notifyRecord.setStatus(NotifyConst.NotifyStatusEnum.FAILED.getCode());
                        }
                    }

                } else {
                    if (notifyRecord.getNotifyTimes() < maxNotifyTimes) {
                        // 判断是否超过重发次数，未超重发次数的，再次进入延迟发送队列
                        queue.offer(new Message(notifyRecord));
                        notifyRecord.setStatus(NotifyConst.NotifyStatusEnum.HTTP_REQUEST_FALIED.getCode());
                    } else {
                        notifyRecord.setStatus(NotifyConst.NotifyStatusEnum.FAILED.getCode());
                    }
                }
                Integer row = notifyFeignClient.updateNotifyRecord(notifyRecord).getData();
                log.info("修改Record记录执行是否成功{}",row);

            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }
}
