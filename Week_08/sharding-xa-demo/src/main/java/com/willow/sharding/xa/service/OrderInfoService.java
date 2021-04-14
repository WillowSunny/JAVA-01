package com.willow.sharding.xa.service;


import com.willow.sharding.xa.domain.OrderInfo;

import java.util.List;

public interface OrderInfoService {

    int saveOrderInfo(OrderInfo orderInfo);

    List<OrderInfo> queryOrderInfo();
}
