package com.willow.sharding.xa.service.impl;


import com.willow.sharding.xa.domain.ItemStock;
import com.willow.sharding.xa.mapper.ItemStockMapper;
import com.willow.sharding.xa.service.ItemStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemStockServiceImpl implements ItemStockService {

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Override
    public int saveItemStock(ItemStock itemStock) {
        return itemStockMapper.insert(itemStock);
    }

    @Override
    public List<ItemStock> queryItemStock() {
        return itemStockMapper.selectAll();
    }

    @Override
    public int reduceStock(long itemId, int num) {
        int n = itemStockMapper.reduceStock(itemId, num);
        if (n <= 0) {
            throw new RuntimeException("reduce fail");
        }
        return n;
    }
}
