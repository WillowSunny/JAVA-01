package com.willow.sharding.xa.domain;

import lombok.Data;

@Data
public class ItemStock {
    private Long id;
    private Long itemId;
    private int stock;

}
