package com.willow.sharding.xa.controller;


import com.willow.sharding.xa.domain.ItemStock;
import com.willow.sharding.xa.service.ItemStockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ItemStockController {

    @Resource
    private ItemStockService itemStockService;

    @GetMapping("/saveItemStock")
    public int saveItemStock(@RequestParam("itemId") long itemId, @RequestParam("num") int num) {
        ItemStock itemStock = new ItemStock();
        itemStock.setItemId(itemId);
        itemStock.setStock(num);
        return itemStockService.saveItemStock(itemStock);
    }

    @GetMapping("/reduceItemStock")
    public int reduceItemStock(@RequestParam("itemId") long itemId, @RequestParam("num") int num) {
        return itemStockService.reduceStock(itemId, 1);
    }

    @GetMapping("/getItemStocks")
    public List<ItemStock> getItemStock() {
        return itemStockService.queryItemStock();
    }
}
