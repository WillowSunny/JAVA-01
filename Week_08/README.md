# 学习笔记

## **Week08 作业题目（周三）：**

**1.（选做）**分析前面作业设计的表，是否可以做垂直拆分。

建表语句：

```mysql
create database ds_0;

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

create database ds_1;

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
```

**2.（必做）**设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

### 1、配置项：

```properties
spring:
    shardingsphere:
        props:
            # 显示具体sql查询情况
            sql-show: true
        datasource:
            names: ds0,ds1
            # 通用配置
            common:
                type: com.zaxxer.hikari.HikariDataSource
                driver-class-name: com.mysql.cj.jdbc.Driver
                username: root
                password: 123456
            ds0:
                jdbc-url: jdbc:mysql://localhost:3306/ds_0?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
            ds1:
                jdbc-url: jdbc:mysql://localhost:3306/ds_1?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
        rules:
            sharding:
                default-database-strategy:
                    standard:
                        sharding-column: id
                        sharding-algorithm-name: database-inline
                tables:
                    tt_order:
                        # 主键算法
                        key-generate-strategy:
                            column: id
                            key-generator-name: snowflake
                        # 配置表规则
                        actual-data-nodes: ds$->{0..1}.tt_order_$->{0..16}
                        # 分库策略
                        database-strategy:
                            standard:
                                sharding-column: user_id
                                sharding-algorithm-name: database-inline
                        # 分表策略
                        table-strategy:
                            standard:
                                sharding-column: id
                                sharding-algorithm-name: table-inline
                # 分片算法配置
                sharding-algorithms:
                    database-inline:
                        type: INLINE
                        props:
                            algorithm-expression: ds$->{user_id % 2}
                    table-inline:
                        type: INLINE
                        props:
                            algorithm-expression: tt_order_$->{id % 16}
                # 键自增算法配置
                key-generators:
                    snowflake:
                        type: SNOWFLAKE
                        props:
                            worker-id: 123
                            max-vibration-offset: 15
```

### 2、结果：

```mysql
mysql> select table_name,table_rows from information_schema.tables where TABLE_SCHEMA = 'ds_0';
+-------------+------------+
| table_name  | table_rows |
+-------------+------------+
| tt_order_0  |         28 |
| tt_order_1  |         30 |
| tt_order_10 |         33 |
| tt_order_11 |         26 |
| tt_order_12 |         34 |
| tt_order_13 |         41 |
| tt_order_14 |         32 |
| tt_order_15 |         34 |
| tt_order_2  |         32 |
| tt_order_3  |         31 |
| tt_order_4  |         36 |
| tt_order_5  |         33 |
| tt_order_6  |         23 |
| tt_order_7  |         31 |
| tt_order_8  |         38 |
| tt_order_9  |         35 |
+-------------+------------+
16 rows in set (0.01 sec)

```

### 3、实现过程遇到的问题：

```java
java.lang.NullPointerException: Cannot invoke method mod() on null object

	at org.codehaus.groovy.runtime.NullObject.invokeMethod(NullObject.java:91)
	at org.codehaus.groovy.runtime.callsite.PogoMetaClassSite.call(PogoMetaClassSite.java:47)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
	at org.codehaus.groovy.runtime.callsite.NullCallSite.call(NullCallSite.java:34)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:128)
	at Script5$_run_closure1.doCall(Script5.groovy:1)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:98)
	at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:325)
	at org.codehaus.groovy.runtime.metaclass.ClosureMetaClass.invokeMethod(ClosureMetaClass.java:264)
	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1034)
	at groovy.lang.Closure.call(Closure.java:420)
	at groovy.lang.Closure.call(Closure.java:414)
	at org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm.doSharding(InlineShardingAlgorithm.java:69)
	at org.apache.shardingsphere.sharding.route.strategy.type.standard.StandardShardingStrategy.doSharding(StandardShardingStrategy.java:67)
	at org.apache.shardingsphere.sharding.route.strategy.type.standard.StandardShardingStrategy.doSharding(StandardShardingStrategy.java:56)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.routeTables(ShardingStandardRoutingEngine.java:214)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.route0(ShardingStandardRoutingEngine.java:194)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.routeByShardingConditionsWithCondition(ShardingStandardRoutingEngine.java:114)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.routeByShardingConditions(ShardingStandardRoutingEngine.java:107)
	at org.apache.shardingsphere.sharding.route.engine.type.standard.ShardingStandardRoutingEngine.getDataNodes(ShardingStandardRoutingEngine.java:84)
```

#### 3.1 出现原因

用来分库分表的列与分库分表的表达式上使用的列不一致。

#### 3.2 解决过程

通过在git上的issue上查找相关的问题，看到有个回复：

```
检查下配置 algorithm-expression: 中的算法列 与sharding-column: 列一致不
```

便检查了自己的配置，确实不一致，经修改后，问题解决。



## **Week08 作业题目（周日）：**

**1.（选做）**列举常见的分布式事务，简单分析其使用场景和优缺点。

https://www.processon.com/view/link/607262dd5653bb5a001e05b6

**2.（必做）**基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。

1、shardingsphere的XA 事务管理器类型默认使用的是Atomikos

### 在配置XA的时候，一直无法启动：

#### 2.1 数据源无法获取到

```java
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration': Initialization of bean failed; nested exception is java.lang.NullPointerException
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:610) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:524) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:410) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1334) ~[spring-beans-5.3.5.jar:5.3.5]
	at 
    
    
Caused by: java.lang.NullPointerException: null
	at org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.getDataSource(SpringBootConfiguration.java:151) ~[sharding-jdbc-spring-boot-starter-4.0.0-RC2.jar:4.0.0-RC2]
	at org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.setEnvironment(SpringBootConfiguration.java:128) ~[sharding-jdbc-spring-boot-starter-4.0.0-RC2.jar:4.0.0-RC2]
	at org.springframework.context.support.ApplicationContextAwareProcessor.invokeAwareInterfaces(ApplicationContextAwareProcessor.java:110) ~[spring-context-5.3.5.jar:5.3.5]
	at org.springframework.context.support.ApplicationContextAwareProcessor.postProcessBeforeInitialization(ApplicationContextAwareProcessor.java:102) ~[spring-context-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization(AbstractAutowireCapableBeanFactory.java:422) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1778) ~[spring-beans-5.3.5.jar:5.3.5]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:602) ~[spring-beans-5.3.5.jar:5.3.5]
	... 80 common frames omitted
```

#### 2.2 引起的原因是，数据源配置为：

```properties
spring.shardingsphere.datasource.names=ds0,ds1

spring.shardingsphere.datasource.common.type=com.zaxxer.hikari.HikariDataSource

spring.shardingsphere.datasource.common.driver-class-name=com.mysql.cj.jdbc.Driver

spring.shardingsphere.datasource.common.username=root

spring.shardingsphere.datasource.common.password=123456

spring.shardingsphere.datasource.ds0.jdbc-url=jdbc:mysql://localhost:3306/ds_0?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai

spring.shardingsphere.datasource.ds1.jdbc-url=jdbc:mysql://localhost:3306/ds_1?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
```

如果将配置修改成单独配置，则能启动成功：

```properties
# 2库  order_info 分库分表  2库 16表
spring.shardingsphere.datasource.names=ds0,ds1

# 配置第 1 个数据源
#spring.shardingsphere.datasource.ds0.type=com.zaxxer.hikari.HikariDataSource
#spring.shardingsphere.datasource.ds0.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.ds0.jdbc-url=jdbc:mysql://localhost:3306/ds_0?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false
#spring.shardingsphere.datasource.ds0.username=root
#spring.shardingsphere.datasource.ds0.password=123456

# 配置第 2 个数据源
#spring.shardingsphere.datasource.ds1.type=com.zaxxer.hikari.HikariDataSource
#spring.shardingsphere.datasource.ds1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.ds1.jdbc-url=jdbc:mysql://localhost:3306/ds_1?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false
#spring.shardingsphere.datasource.ds1.username=root
#spring.shardingsphere.datasource.ds1.password=123456
```

这个问题，折腾了一天了，一开始用的单独分开配置数据源的方式，启动也是报了一个什么错，当时忘记记录了。然后去看git上的Issue，就有个解决方案是用common的方式配置，就改了，确实可以，能启动成功，但是在执行代码的时候就说找不到XA的事务管理器。网上的资料也一直没有正确的解决方式，再加上shardingsphere的事务模块的版本很多，有些人用的是这种，有些人用的又是另一个，导致在版本间换来换去都无法确认问题究竟出在哪里。

最后实在没办法了，找了其他同学的git上作业，拿下来后，将表信息改成自己的，发现是可以运行的，后来再对比了一下同学的和自己的配置的差异，发现差别就是上面说到数据源配置。

XA项目地址：

[sharding-xa-demo]: https://github.com/WillowSunny/JAVA-01/tree/main/Week_08/sharding-xa-demo

