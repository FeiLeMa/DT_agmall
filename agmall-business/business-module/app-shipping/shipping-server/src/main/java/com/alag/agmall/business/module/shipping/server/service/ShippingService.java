package com.alag.agmall.business.module.shipping.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.shipping.api.model.Shipping;
import com.github.pagehelper.PageInfo;

public interface ShippingService {
    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse del(Integer userId, Integer id);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer id, Integer shippingId);

    ServerResponse<PageInfo> list(Integer id, Integer pageNum, Integer pageSize);

    ServerResponse<Shipping> getShippingById(Integer id);
}
