package com.alag.agmall.business.module.alipay.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.alipay.api.model.AlipayInfo;

import java.util.Map;

public interface AlipayService {

    ServerResponse<String> pay(Integer id, Long orderNo);

    ServerResponse aNotifyBack(Map<String, String> paramMap);

    ServerResponse<AlipayInfo> selectByOrderNo(Long orderNo);
}
