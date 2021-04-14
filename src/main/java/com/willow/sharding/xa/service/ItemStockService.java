package com.willow.sharding.xa.service;


import com.willow.sharding.xa.domain.ItemStock;

import java.util.List;

public interface ItemStockService {

    int saveItemStock(ItemStock itemStock);

    List<ItemStock> queryItemStock();

    int reduceStock(long itemId, int num);
}
