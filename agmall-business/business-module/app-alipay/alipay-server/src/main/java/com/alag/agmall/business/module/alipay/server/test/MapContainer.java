package com.alag.agmall.business.module.alipay.server.test;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MapContainer {
    public static Set<String> orderID = Sets.newHashSet();
}
