package com.alag.agmall.business.module.message.api;

import com.alag.agmall.business.module.message.api.model.TransactionMessage;

import java.util.Date;

public class Test1 {
    public static void main(String[] args) {
        TransactionMessage tm = TransactionMessage.newTransactionMessage((message -> {
            message.setCreateTime(new Date());
            message.setMessageBody("nimabi");
        }));
        System.out.println(tm);
    }
}
