package com.alag.agmall.business.module.notify.server.service;


import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.NotifyPageParamBean;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.github.pagehelper.PageInfo;

public interface NotifyRecordService {
    void sendNotify(String notifyUrl, String merchantOrderNo, String merchantNo);

    ServerResponse<NotifyRecord> getById(String id);

    ServerResponse<NotifyRecord> getByMNoAndMOrderNoAndNType(String merchantNo, String merchantOrderNo, String notifyType);

    ServerResponse<PageInfo> list(NotifyPageParamBean notifyPageParamBean);

    ServerResponse<Integer> addRecord(NotifyRecord notifyRecord);

    ServerResponse<Integer> modifyRecord(NotifyRecord notifyRecord);
}
