package com.willow.sharding;

import com.willow.sharding.domain.Order;
import com.willow.sharding.repository.OrderRepository;
import com.willow.sharding.service.XAOrderService;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShardingBaseTableApplicationTests {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private XAOrderService xaOrderService;
	@Test
	void contextLoads() {
		Random random = new Random();
		final Date date = new Date();

		for (int i = 0; i < 10_00; i++) {
			final Order order = new Order();
			order.setPayStatus(1);
			order.setCreateTime(date);
			order.setUserId((long) (Math.random() * 1024));
			order.setSerialNum(123L+random.nextInt(1000000));
			orderRepository.save(order);
		}
	}

	@Test
	void transaction() {
		assertThat(xaOrderService.insert(10), is(TransactionType.XA));
//        assertThat(orderService.selectAll(), is(10));
	}

}
