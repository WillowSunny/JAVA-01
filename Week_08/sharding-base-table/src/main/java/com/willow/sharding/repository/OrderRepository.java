package com.willow.sharding.repository;

import com.willow.sharding.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
