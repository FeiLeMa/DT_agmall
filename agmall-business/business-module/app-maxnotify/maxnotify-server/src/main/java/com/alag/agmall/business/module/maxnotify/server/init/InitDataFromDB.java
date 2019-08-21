package com.alag.agmall.business.module.maxnotify.server.init;

import com.alag.agmall.business.core.page.NotifyPageParamBean;
import com.alag.agmall.business.module.maxnotify.server.delay.Message;
import com.alag.agmall.business.module.maxnotify.server.delay.Task;
import com.alag.agmall.business.module.maxnotify.server.thread.MyTreadPool;
import com.alag.agmall.business.module.notify.api.Const.NotifyConst;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alag.agmall.business.module.notify.feign.NotifyFeignClient;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
@Order(value = 2)
public class InitDataFromDB implements ApplicationRunner {
    @Autowired
    private InitNotifyParam initNotifyParam;
    @Autowired
    private NotifyFeignClient notifyFeignClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("===>init get notify data from database");
        NotifyPageParamBean notifyPageParamBean = NotifyPageParamBean.setReturn(pageParam -> {
            pageParam.setPageNum(1);
            pageParam.setPageSize(10);

            pageParam.setField1(NotifyConst.NotifyStatusEnum.FAILED.getCode());
            pageParam.setField2(NotifyConst.NotifyStatusEnum.SUCCESS.getCode());
            pageParam.setNotifyTimes(initNotifyParam.getMaxNotifyTimes());
            pageParam.setListPageSortType("ASC");
            return pageParam;
        });

        PageInfo data = notifyFeignClient.queryNotifyRecordListPage(notifyPageParamBean).getData();
        List<NotifyRecord> notifyRecords = data.getList();

        List<NotifyRecord> allNotiRecord = Lists.newArrayList();
        allNotiRecord.addAll(notifyRecords);

        for (int i = 2; i < data.getPages()+1; i++) {
            notifyPageParamBean.setPageNum(i);
            notifyRecords = notifyFeignClient.queryNotifyRecordListPage(notifyPageParamBean).getData().getList();
            allNotiRecord.addAll(notifyRecords);
        }

        Task task = Task.getInstance(initNotifyParam,notifyFeignClient);
        Iterator<NotifyRecord> it = allNotiRecord.iterator();
        while (it.hasNext()) {
            String s = JSONObject.toJSONString(it.next());
            NotifyRecord notifyRecord = JSONObject.parseObject(s, NotifyRecord.class);
            task.getDelayQueue().offer(new Message(notifyRecord));
        }

        MyTreadPool.exector.submit(task);
    }
}
