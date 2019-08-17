package com.alag.agmall.business.module.notify.server.service.impl;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.notify.api.Const.IDGenerator;
import com.alag.agmall.business.module.notify.api.model.NotifyRecordLog;
import com.alag.agmall.business.module.notify.server.mapper.NotifyRecordLogMapper;
import com.alag.agmall.business.module.notify.server.service.NotifyRecordLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-17 15:35
 * @email: alag256@aliyun.com
 **/

@Service
public class NotifyRecordLogServiceImpl implements NotifyRecordLogService {
    @Autowired
    private NotifyRecordLogMapper notifyRecordLogMapper;

    @Override
    public ServerResponse<Integer> addRecordLog(NotifyRecordLog notifyRecordLog) {
        notifyRecordLog.setFields(nRLog->{
            nRLog.setId(IDGenerator.notifyRecordIDBuilder());
            nRLog.setCreateTime(new Date());
            nRLog.setUpdateTime(new Date());
        });
        int row = notifyRecordLogMapper.insertSelective(notifyRecordLog);
        return ServerResponse.createBySuccess(row);
    }
}
