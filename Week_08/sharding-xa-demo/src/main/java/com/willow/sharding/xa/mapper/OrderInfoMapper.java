package com.willow.sharding.xa.mapper;

import com.willow.sharding.xa.domain.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper  {
    int insert(OrderInfo orderInfo);

    List<OrderInfo> selectAll();
}
