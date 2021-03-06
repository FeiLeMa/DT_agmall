package com.alag.agmall.business.module.message.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.ParamBean;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.github.pagehelper.PageInfo;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pay_transaction")
public interface TransactionMessageController {

    /**
     * 预存储消息
     */
    @PostMapping("prestore_message")
    ServerResponse<Integer> saveMessageWaitingConfirm(@RequestBody TransactionMessage transactionMessage);

    /**
     * 确认并发送消息.
     */
    @PostMapping("confirm_and_send_message")
    HystrixCommand<ServerResponse> confirmAndSendMessage(@RequestParam("messageId") String messageId);

    /**
     * 存储并发送消息.
     * 比如：银行通知，它存储不成功，银行会继续发送
     */
    @PostMapping("save_and_send_message")
    ServerResponse<Integer> saveAndSendMessage(@RequestBody TransactionMessage transactionMessage);

    /**
     * 直接发送消息.
     * 透传到MQ
     */
    @PostMapping("direct_send_message")
    void directSendMessage(@RequestBody TransactionMessage transactionMessage);


    /**
     * 重发消息.
     * 对消息恢复系统取到的确认发送的但消费方未消费的消息再次发送
     */
    @PostMapping("r_send_message")
    void reSendMessage(@RequestBody TransactionMessage transactionMessage);


    /**
     * 根据messageId重发某条消息.
     */
    @PostMapping("r_send_message_by_id")
    void reSendMessageByMessageId(@RequestParam("messageId") String messageId);


    /**
     * 将消息标记为死亡消息.
     */
    @PutMapping("set_message_status")
    void setMessageToAreadlyDead(@RequestParam("messageId") String messageId);


    /**
     * 根据消息ID获取消息
     */
    @GetMapping("get_message")
    ServerResponse<TransactionMessage> getMessageByMessageId(@RequestParam("messageId") String messageId);

    /**
     * 根据消息ID删除消息
     */
    @DeleteMapping("del_message")
    HystrixCommand<ServerResponse> deleteMessageByMessageId(@RequestParam("messageId") String messageId);


    /**
     * 重发某个消息队列中的全部已死亡的消息.
     */
    @PostMapping("r_send_all_message")
    void reSendAllDeadMessageByQueueName(@RequestParam("queueName") String queueName,
                                         @RequestParam("batchSize") int batchSize);

    /**
     * 获取分页数据
     */
    @PostMapping("list_page")
    ServerResponse<PageInfo> listPage(@RequestBody ParamBean paramBean);

}
