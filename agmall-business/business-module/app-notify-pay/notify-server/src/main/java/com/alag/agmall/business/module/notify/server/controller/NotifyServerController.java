package com.alag.agmall.business.module.notify.server.controller;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.NotifyPageParamBean;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alag.agmall.business.module.notify.api.model.NotifyRecordLog;
import com.alag.agmall.business.module.notify.server.service.NotifyRecordLogService;
import com.alag.agmall.business.module.notify.server.service.NotifyRecordService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notify")
public class NotifyServerController {
    @Autowired
    private NotifyRecordService notifyRecordService;

    @Autowired
    private NotifyRecordLogService notifyRecordLogService;

    /**
     * 发送消息通知
     *
     * @param notifyUrl       通知地址
     * @param merchantOrderNo 商户订单号
     * @param merchantNo      商户编号
     */
    @RequestMapping("send")
    public ServerResponse notifySend(@RequestParam("notifyUrl") String notifyUrl,
                                     @RequestParam("merchantOrderNo") String merchantOrderNo,
                                     @RequestParam("merchantNo") String merchantNo) {

        if (StringUtils.isAnyBlank(notifyUrl, merchantNo, merchantOrderNo)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        notifyRecordService.sendNotify(notifyUrl, merchantOrderNo, merchantNo);
        return ServerResponse.createBySuccessMessage("发送成功");
    }

    /**
     * 通过ID获取通知记录
     *
     * @param id
     * @return
     */
    @RequestMapping("get")
    public ServerResponse<NotifyRecord> getNotifyRecordById(@RequestParam("id") String id) {
        if (StringUtils.isBlank(id)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return notifyRecordService.getById(id);
    }

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     *
     * @param merchantNo      商户编号
     * @param merchantOrderNo 商户订单号
     * @param notifyType      消息类型
     * @return
     */
    @RequestMapping("get_by_condition")
    public ServerResponse<NotifyRecord> getByCondition(@RequestParam("merchantNo") String merchantNo,
                                                       @RequestParam("merchantOrderNo") String merchantOrderNo,
                                                       @RequestParam("notifyType") String notifyType) {
        if (StringUtils.isAnyBlank(merchantNo, merchantOrderNo,notifyType)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return notifyRecordService.getByMNoAndMOrderNoAndNType(merchantNo, merchantOrderNo, notifyType);
    }

    @RequestMapping("list_record")
    public ServerResponse<PageInfo> queryNotifyRecordListPage(@RequestBody NotifyPageParamBean notifyPageParamBean) {
        return notifyRecordService.list(notifyPageParamBean);
    }

    /**
     * 创建消息通知
     *
     * @param notifyRecord
     */
    @RequestMapping("create_record")
    public ServerResponse<Integer> createNotifyRecord(@RequestBody NotifyRecord notifyRecord) {
        return notifyRecordService.addRecord(notifyRecord);
    }

    /**
     * 修改消息通知
     *
     * @param notifyRecord
     */
    @RequestMapping("modify")
    public ServerResponse<Integer> updateNotifyRecord(@RequestBody NotifyRecord notifyRecord) {
        return notifyRecordService.modifyRecord(notifyRecord);
    }

    /**
     * 创建消息通知记录
     *
     * @param notifyRecordLog
     * @return
     */
    @RequestMapping("create_record_log")
    public ServerResponse<Integer> createNotifyRecordLog(@RequestBody NotifyRecordLog notifyRecordLog) {
        return notifyRecordLogService.addRecordLog(notifyRecordLog);
    }

}
