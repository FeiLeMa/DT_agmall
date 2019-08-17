package com.alag.agmall.business.module.notify.api.Const;

import java.util.UUID;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-17 18:20
 * @email: alag256@aliyun.com
 **/
public class IDGenerator {

    public static String notifyRecordIDBuilder() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
