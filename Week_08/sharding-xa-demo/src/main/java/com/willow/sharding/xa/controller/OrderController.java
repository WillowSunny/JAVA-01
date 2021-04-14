package com.willow.sharding.xa.controller;


import com.willow.sharding.xa.domain.OrderInfo;
import com.willow.sharding.xa.service.ItemStockService;
import com.willow.sharding.xa.service.OrderInfoService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@RestController
public class OrderController {
    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private ItemStockService itemStockService;

    @GetMapping("/saveOrder")
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(value = TransactionType.XA)
    public int saveOrderInfo(@RequestParam("itemId") long itemId, @RequestParam("num") int num) {
        Random random = new Random();
        long userId;
        while (true) {
            userId = random.nextLong();
            if (userId > 0) {
                break;
            }
        }
        // todo  id分库，user_id 分表到不同的ds和table
        // 这样就会将同一个itemId = 891444513857666666的订单存错到不同的库中
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(itemId);
        orderInfo.setSerialNum(itemId & userId);
        orderInfo.setOrderAmount(num);
        orderInfo.setUserId(userId);

        // 创建订单
        orderInfoService.saveOrderInfo(orderInfo);
        // 减库存
        itemStockService.reduceStock(itemId, num);
        return 1;
    }

    @GetMapping("/getOrders")
    public List<OrderInfo> getUsers() {
        return orderInfoService.queryOrderInfo();
    }
}
