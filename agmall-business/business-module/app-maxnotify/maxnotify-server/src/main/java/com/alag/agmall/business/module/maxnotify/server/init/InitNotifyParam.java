package com.alag.agmall.business.module.maxnotify.server.init;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "notifyparam")
@Order(value = 1)
public class InitNotifyParam {

    private Map<Integer, Integer> notifyTimeMaps;
    private String successValue;

    /**
     * 最大通知次数限制.
     * @return
     */
    public Integer getMaxNotifyTimes() {
        return notifyTimeMaps == null ? 0 : notifyTimeMaps.size();
    }
}
