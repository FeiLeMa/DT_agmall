package com.alag.agmall.business.module.notify.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.notify.api.model.NotifyRecordLog;

public interface NotifyRecordLogService {
    ServerResponse<Integer> addRecordLog(NotifyRecordLog notifyRecordLog);
}
