package com.willow.sharding.service;

import com.willow.sharding.domain.Order;
import com.willow.sharding.repository.OrderRepository;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

@Service
public class XAOrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public TransactionType insert(final int count) {
        doInsert(count);

        return TransactionTypeHolder.get();
    }

    private void doInsert(final int count){
        Random random = new Random();
        final Date date = new Date();
        for (int i = 0; i < count; i++) {
            final Order order = new Order();
            order.setPayStatus(1);
            order.setCreateTime(date);
            order.setUserId((long) (Math.random() * 1024));
            order.setSerialNum(123L+random.nextInt(1000000));
            orderRepository.save(order);
        }
    }
}
