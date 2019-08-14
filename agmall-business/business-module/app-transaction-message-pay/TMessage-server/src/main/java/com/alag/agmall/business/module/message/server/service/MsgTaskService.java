package com.alag.agmall.business.module.message.server.service;

public interface MsgTaskService {
    void handleWaitConfirmMessage();

    void handleConfirmSendingMessage();
}
