package com.willow.sharding.xa.service.impl;


import com.willow.sharding.xa.domain.OrderInfo;
import com.willow.sharding.xa.mapper.OrderInfoMapper;
import com.willow.sharding.xa.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> queryOrderInfo() {
        List<OrderInfo> orderInfos = orderInfoMapper.selectAll();
        return orderInfos;
    }

    @Override
    public int saveOrderInfo(OrderInfo orderInfo) {
        return orderInfoMapper.insert(orderInfo);
    }

}
