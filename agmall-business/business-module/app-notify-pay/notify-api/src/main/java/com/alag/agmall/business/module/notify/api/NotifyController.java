package com.alag.agmall.business.module.notify.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.NotifyPageParamBean;
import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alag.agmall.business.module.notify.api.model.NotifyRecordLog;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notify")
public interface NotifyController {

    /**
     * 发送消息通知
     *
     * @param notifyUrl       通知地址
     * @param merchantOrderNo 商户订单号
     * @param merchantNo      商户编号
     */
    @RequestMapping("send")
    void notifySend(@RequestParam("notifyUrl") String notifyUrl,
                    @RequestParam("merchantOrderNo")String merchantOrderNo,
                    @RequestParam("merchantNo")String merchantNo);


    /**
     * 通过ID获取通知记录
     *
     * @param id
     * @return
     */
    @RequestMapping("get")
    ServerResponse<NotifyRecord> getNotifyRecordById(@RequestParam("id")String id);

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     *
     * @param merchantNo      商户编号
     * @param merchantOrderNo 商户订单号
     * @param notifyType      消息类型
     * @return
     */
    @RequestMapping("get_by_condition")
    ServerResponse<NotifyRecord> getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(@RequestParam("merchantNo")String merchantNo,
                                                                                      @RequestParam("merchantOrderNo")String merchantOrderNo,
                                                                                      @RequestParam("notifyType")String notifyType);

    @PostMapping("list_record")
    ServerResponse<PageInfo> queryNotifyRecordListPage(@RequestBody NotifyPageParamBean notifyPageParamBean);

    /**
     * 创建消息通知
     *
     * @param notifyRecord
     */
    @RequestMapping("create_record")
    ServerResponse<Integer> createNotifyRecord(@RequestBody NotifyRecord notifyRecord);

    /**
     * 修改消息通知
     *
     * @param notifyRecord
     */
    @RequestMapping("modify")
    void updateNotifyRecord(@RequestBody NotifyRecord notifyRecord);

    /**
     * 创建消息通知记录
     *
     * @param notifyRecordLog
     * @return
     */
    @RequestMapping("create_record_log")
    ServerResponse<Integer> createNotifyRecordLog(@RequestBody NotifyRecordLog notifyRecordLog);
}
