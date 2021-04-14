package com.willow.sharding.xa.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderInfo implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 流水号
     */
    private Long serialNum;
    /**
     * 用户id
     */
    private Long userId;

    private Integer payStatus;
    private Integer tradeStatus;
    private Integer orderAmount;
    private Integer payAmount;
    private Integer logisticsFees;
    private String logisticsStatus;
    private Integer updateTime;
    private Date createTime;
    private String note;

}
