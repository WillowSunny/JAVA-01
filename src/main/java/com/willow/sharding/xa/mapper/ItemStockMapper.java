package com.willow.sharding.xa.mapper;


import com.willow.sharding.xa.domain.ItemStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemStockMapper {
    int insert(ItemStock itemStock);

    List<ItemStock> selectAll();

    int reduceStock(@Param("itemId") long itemId, @Param("num") int num);
}
