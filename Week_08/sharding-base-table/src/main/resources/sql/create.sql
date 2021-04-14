CREATE TABLE `tt_order_0` (
	`id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT 'id',
	`serial_num` BIGINT(20) NULL DEFAULT '0' COMMENT '流水号',
	`user_id` BIGINT(20) NULL DEFAULT NULL COMMENT '用户id',
	`trade_status` TINYINT(4) NULL DEFAULT '0' COMMENT '0=进行中，1=已完成，2=取消交易，3=已结算',
	`pay_status` TINYINT(4) NULL DEFAULT '1' COMMENT '1=未付款，2=已付款',
	`order_amount` INT(11) NULL DEFAULT '0' COMMENT '订单金额/分',
	`pay_amount` INT(11) NULL DEFAULT '0' COMMENT '付款金额/分',
	`logistics_fees` INT(11) NULL DEFAULT '0' COMMENT '物流费用/分',
	`logistics_status` VARCHAR(64) NULL DEFAULT '' COMMENT '物流状态' COLLATE 'utf8mb4_unicode_ci',
	`update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
	`note` VARCHAR(64) NULL DEFAULT '' COMMENT '备注' COLLATE 'utf8mb4_unicode_ci',
	PRIMARY KEY (`id`)
)
COMMENT='订单信息'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;



CREATE TABLE tt_order_1 LIKE tt_order_0;
CREATE TABLE tt_order_2 LIKE tt_order_0;
CREATE TABLE tt_order_3 LIKE tt_order_0;
CREATE TABLE tt_order_4 LIKE tt_order_0;
CREATE TABLE tt_order_5 LIKE tt_order_0;
CREATE TABLE tt_order_6 LIKE tt_order_0;
CREATE TABLE tt_order_7 LIKE tt_order_0;
CREATE TABLE tt_order_8 LIKE tt_order_0;
CREATE TABLE tt_order_9 LIKE tt_order_0;
CREATE TABLE tt_order_10 LIKE tt_order_0;
CREATE TABLE tt_order_11 LIKE tt_order_0;
CREATE TABLE tt_order_12 LIKE tt_order_0;
CREATE TABLE tt_order_13 LIKE tt_order_0;
CREATE TABLE tt_order_14 LIKE tt_order_0;
CREATE TABLE tt_order_15 LIKE tt_order_0;