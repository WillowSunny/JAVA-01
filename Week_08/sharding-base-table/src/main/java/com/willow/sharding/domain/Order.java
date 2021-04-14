package com.willow.sharding.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tt_order")
public class Order implements Serializable {
    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 流水号
     */
    private Long serialNum;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    private Integer payStatus;

    private Integer orderAmount;
    private Integer payAmount;
    private Integer logisticsFees;
    private String logisticsStatus;
    private Integer updateTime;
    private Date createTime;
    private String note;

}
