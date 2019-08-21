package com.alag.agmall.business.module.maxnotify.server.delay;

import com.alag.agmall.business.module.notify.api.model.NotifyRecord;
import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Message implements Delayed {
    private NotifyRecord msgBody;
    private long excuteTime;


    public Message(NotifyRecord msgBody) {
        this.msgBody = msgBody;
        this.excuteTime = getExcuteTime();
    }

    private long getExcuteTime() {
        //上次通知时间
        long lastNotifyTime = this.msgBody.getUpdateTime().getTime();
        Integer notifyTimes = this.msgBody.getNotifyTimes();//已经通知次数

        String notifyRule = this.msgBody.getNotifyRule();
        Map<Integer,Integer> notifyRuleMap = JSON.parseObject(notifyRule, Map.class);
        //下次通知时间间隔
        Object obj = notifyRuleMap.get(notifyTimes+1+"");
        Integer nextIntervalN = Integer.valueOf(obj.toString());
        long nextNotifyTime = lastNotifyTime + nextIntervalN * 1000*10;

        return nextNotifyTime;
    }


    public NotifyRecord getMsgBody() {
        return msgBody;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.excuteTime - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Message msg = (Message)o;
        return this.excuteTime > msg.excuteTime ? 1 : (this.excuteTime < msg.excuteTime ? -1 : 0);
    }
}
