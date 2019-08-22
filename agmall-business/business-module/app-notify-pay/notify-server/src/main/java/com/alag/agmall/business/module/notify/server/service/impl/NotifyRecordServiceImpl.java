package com.alag.agmall.business.module.notify.server.service.impl;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.NotifyPageParamBean;
import com.alag.agmall.business.module.notify.api.Const.NotifyConst;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alag.agmall.business.module.notify.server.mapper.NotifyRecordMapper;
import com.alag.agmall.business.module.notify.server.service.NotifyRecordService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-17 15:35
 * @email: alag256@aliyun.com
 **/

@Service
@Slf4j
public class NotifyRecordServiceImpl implements NotifyRecordService {
    @Autowired
    private JmsTemplate notifyJmsTemplate;
    @Autowired
    private NotifyRecordMapper notifyRecordMapper;

    @Override
    public void sendNotify(String notifyUrl, String merchantOrderNo, String merchantNo) {

        NotifyRecord record = NotifyRecord.setReturn(notifyRecord -> {
            notifyRecord.setNotifyTimes(0);
            notifyRecord.setLimitNotifyTimes(5);
            notifyRecord.setStatus(NotifyConst.NotifyStatusEnum.CREATED.getCode());
            notifyRecord.setUrl(notifyUrl);
            notifyRecord.setMerchantOrderNo(merchantOrderNo);
            notifyRecord.setMerchantNo(merchantNo);
            notifyRecord.setNotifyType(NotifyConst.NotifyTypeEnum.MERCHANT.getCode());
            return notifyRecord;
        });

        final String recordJson = JSONObject.toJSONString(record);
        notifyJmsTemplate.setDefaultDestinationName(NotifyConst.Notify.QUEUE_NAME);
        notifyJmsTemplate.send(session -> session.createTextMessage(recordJson));
    }

    @Override
    public ServerResponse<NotifyRecord> getById(String id) {
        NotifyRecord notifyRecord = notifyRecordMapper.selectByPrimaryKey(id);
        return ServerResponse.createBySuccess(notifyRecord);
    }

    @Override
    public ServerResponse<NotifyRecord> getByMNoAndMOrderNoAndNType(String merchantNo, String merchantOrderNo, String notifyType) {
        NotifyRecord notifyRecord = notifyRecordMapper.selectByCondition(merchantNo, merchantOrderNo, notifyType);
        return ServerResponse.createBySuccess(notifyRecord);
    }

    @Override
    public ServerResponse<PageInfo> list(NotifyPageParamBean notifyPageParamBean) {
        PageHelper.startPage(notifyPageParamBean.getPageNum(), notifyPageParamBean.getPageSize());
        List<NotifyRecord> notifyRecordList = notifyRecordMapper.selectListByCondition(notifyPageParamBean);
        PageInfo pageInfo = new PageInfo(notifyRecordList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<Integer> addRecord(NotifyRecord notifyRecord) {
        int row = notifyRecordMapper.insertSelective(notifyRecord);
        return ServerResponse.createBySuccess(row);
    }

    @Override
    public ServerResponse<Integer> modifyRecord(NotifyRecord notifyRecord) {
        int row = notifyRecordMapper.updateByPrimaryKeySelective(notifyRecord);
        return ServerResponse.createBySuccess(row);
    }
}
