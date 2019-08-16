package com.alag.agmall.business.module.message.feign.fallback;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.page.ParamBean;
import com.alag.agmall.business.module.message.api.model.TransactionMessage;
import com.alag.agmall.business.module.message.feign.controller.TransactionMessageFeign;
import com.github.pagehelper.PageInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import feign.hystrix.FallbackFactory;
import com.netflix.hystrix.HystrixCommand.Setter;
import org.springframework.stereotype.Component;

@Component
public class MyFallbackFactory implements FallbackFactory<TransactionMessageFeign> {
    private static final Setter hystrixCommandGroupKey =
            Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("TransactionMessageFeign"));
    @Override
    public TransactionMessageFeign create(Throwable throwable) {
        return new TransactionMessageFeign() {
            @Override
            public ServerResponse<Integer> saveMessageWaitingConfirm(TransactionMessage transactionMessage) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public HystrixCommand<ServerResponse> confirmAndSendMessage(String messageId) {
                return new HystrixCommand<ServerResponse>(hystrixCommandGroupKey) {
                    @Override
                    protected ServerResponse run() throws Exception {
                        return ServerResponse.createByErrorMessage(throwable.getMessage());
                    }
                };
            }

            @Override
            public ServerResponse<Integer> saveAndSendMessage(TransactionMessage transactionMessage) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public void directSendMessage(TransactionMessage transactionMessage) {
            }

            @Override
            public void reSendMessage(TransactionMessage transactionMessage) {
            }

            @Override
            public void reSendMessageByMessageId(String messageId) {
            }

            @Override
            public void setMessageToAreadlyDead(String messageId) {
            }

            @Override
            public ServerResponse<TransactionMessage> getMessageByMessageId(String messageId) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }

            @Override
            public HystrixCommand<ServerResponse> deleteMessageByMessageId(String messageId) {
                return new HystrixCommand<ServerResponse>(hystrixCommandGroupKey) {
                    @Override
                    protected ServerResponse run() throws Exception {
                        return ServerResponse.createByErrorMessage(throwable.getMessage());
                    }
                };
            }

            @Override
            public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) {
            }

            @Override
            public ServerResponse<PageInfo> listPage(ParamBean paramBean) {
                return ServerResponse.createByErrorMessage(throwable.getMessage());
            }
        };
    }
}
