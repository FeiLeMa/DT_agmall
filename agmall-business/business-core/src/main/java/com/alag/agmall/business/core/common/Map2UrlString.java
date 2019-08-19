package com.alag.agmall.business.core.common;

import com.alag.agmall.business.core.util.DateTimeUtil;
import com.alag.agmall.business.core.util.PropertiesUtil;

import java.util.Date;
import java.util.Map;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-18 22:06
 * @email: alag256@aliyun.com
 **/
public class Map2UrlString {

    public static String work(Map map) {
        String payment = map.get("payment").toString();
        String paymentTime = map.get("paymentTime").toString();
        String userId = map.get("userId").toString();
        String createTime = DateTimeUtil.dateToStr((Date) map.get("createTime"));
        String merchantUrl = PropertiesUtil.getProperty(Const.Property.MERCHANT_URL);
        String status = map.get("status").toString();
        StringBuilder stringBuilder = new StringBuilder();
        String url = stringBuilder.append(merchantUrl).append("payment=").append(payment)
                .append("&paymentTime=").append(paymentTime)
                .append("&userId=").append(userId)
                .append("&createTime=").append(createTime)
                .append("&status=").append(status)
                .toString();

        return url;
    }
}
