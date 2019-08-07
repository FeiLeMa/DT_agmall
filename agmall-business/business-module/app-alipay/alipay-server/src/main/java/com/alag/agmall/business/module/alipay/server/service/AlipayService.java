package com.alag.agmall.business.module.alipay.server.service;

import com.alag.agmall.business.core.common.ServerResponse;

import java.util.Map;

public interface AlipayService {

    ServerResponse<String> pay(Integer id, Long orderNo);

    ServerResponse aliCallback(Map<String, String> params);
}
