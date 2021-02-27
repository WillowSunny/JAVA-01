学习笔记

```sql
- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.29 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 java1 的数据库结构
CREATE DATABASE IF NOT EXISTS `java1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `java1`;

-- 导出  表 java1.ts_user 结构
CREATE TABLE IF NOT EXISTS `ts_user` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `account` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称',
  `password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `telephone` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态：1=正常，0=失效，2=停封',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 正在导出表  java1.ts_user 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ts_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_user` ENABLE KEYS */;

-- 导出  表 java1.ts_user_receives_address 结构
CREATE TABLE IF NOT EXISTS `ts_user_receives_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT 'userid',
  `receiver` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收件人',
  `telephone` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `countries` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '登陆国家',
  `provinces` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '省',
  `city` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '市',
  `area` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '区',
  `detail` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '详细地址',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `note` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收货地址';

-- 正在导出表  java1.ts_user_receives_address 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ts_user_receives_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_user_receives_address` ENABLE KEYS */;

-- 导出  表 java1.tt_goods 结构
CREATE TABLE IF NOT EXISTS `tt_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `img_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品图片url',
  `title` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品标题',
  `price` int(11) DEFAULT NULL COMMENT '商品价格/分',
  `shelves` tinyint(4) DEFAULT NULL COMMENT '0=下架，1=上架',
  `status` tinyint(4) DEFAULT NULL COMMENT '0=失效，1=正常',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `note` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品信息';

-- 正在导出表  java1.tt_goods 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `tt_goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_goods` ENABLE KEYS */;

-- 导出  表 java1.tt_order 结构
CREATE TABLE IF NOT EXISTS `tt_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `trade_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0=进行中，1=已完成，2=取消交易，3=已结算',
  `pay_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1=未付款，2=已付款',
  `order_amount` int(11) DEFAULT '0' COMMENT '订单金额/分',
  `pay_amount` int(11) DEFAULT '0' COMMENT '付款金额/分',
  `logistics_fees` int(11) DEFAULT '0' COMMENT '物流费用/分',
  `logistics_status` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '物流状态',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `note` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单信息';

-- 正在导出表  java1.tt_order 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `tt_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `tt_order` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

```

