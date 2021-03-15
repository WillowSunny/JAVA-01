# 学习笔记

## Week07 作业题目（周日）-第2题

读写分离 - 动态切换数据源版本 1.0

建表语句：

```mysql
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '主键',
  `account` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户账号',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `password` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `telephone` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态：1=正常，0=失效，2=停封',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表' ;


```

解答过程：

代码在`multiple-datasource-01` 项目中，使用Springboot自带的hikari，spring jdbc template 实现。

第一步：配置主从库

```properties
spring.datasource.master.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.master.driverClassName = com.mysql.cj.jdbc.Driver
spring.datasource.master.jdbcUrl = jdbc:mysql://localhost:3306/java1?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=true
spring.datasource.master.username = willow
spring.datasource.master.password = 123456

spring.datasource.slave.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.slave.driverClassName = com.mysql.cj.jdbc.Driver
spring.datasource.slave.jdbcUrl = jdbc:mysql://localhost:3306/java2?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=true
spring.datasource.slave.username = willow
spring.datasource.slave.password = 123456
```

第二步：配置主从库数据源

主库：

```java
@Configuration
public class MasterDataSourceConfig {

    @Primary
    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource getMasterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("masterJdbcTemplate")
    JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource")DataSource masterDataSource){
        return new JdbcTemplate(masterDataSource);
    }

}
```

从库：

```java
@Configuration
public class SlaveDataSourceConfig {

    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource getSlaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("slaveJdbcTemplate")
    JdbcTemplate masterJdbcTemplate(@Qualifier("slaveDataSource")DataSource slaveDataSource){
        return new JdbcTemplate(slaveDataSource);
    }
}
```

第三步：封装Dao处理

```java
@Component
public class UserManagerImpl implements UserManager {

    @Resource(name = "masterJdbcTemplate")
    private JdbcTemplate masterTemplate;

    @Resource(name = "slaveJdbcTemplate")
    private JdbcTemplate slaveTemplate;

    @Override
    public List<User> listUser() {
        return slaveTemplate.query("select * from user", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int insert(String account,String name) {
        return masterTemplate.update("insert into user(account,name) values (?,?)",account,name);
    }
}
```

第四步：写个Controller测试

```java
@RestController
public class DataSourceTestController {

    @Resource(name = "masterJdbcTemplate")
    private JdbcTemplate masterTemplate;

    @Resource(name = "slaveJdbcTemplate")
    private JdbcTemplate slaveTemplate;

    @Autowired
    private UserManager userManager;

    @GetMapping("/master")
    public String master(@RequestParam(value = "name", defaultValue = "World") String name) throws SQLException, ClassNotFoundException {
        masterTemplate.update("insert into user(account,name) values (?,?)","123123","willow-master");
        return String.format("Hello %s!", name);
    }

    @GetMapping("/slave")
    public String slave(@RequestParam(value = "name", defaultValue = "World") String name) throws SQLException, ClassNotFoundException {
        slaveTemplate.update("insert into user(account,name) values (?,?)","123456","willow-slave");
        return String.format("Hello %s!", name);
    }

    @GetMapping("/masterInsert")
    public String masterInsert(@RequestParam(value = "account", defaultValue = "World") String account,@RequestParam(value = "name", defaultValue = "World") String name){
        userManager.insert(account, name);
        return String.format("Hello %s!", name);
    }

    @GetMapping("/slaveQuery")
    public String slaveQuery(@RequestParam(value = "account", defaultValue = "World") String account){
        List<User> users = userManager.listUser();
        return String.format("Hello %s!", users.get(0).getName());
    }
}
```



## Week07 作业题目（周日）-第3题

题目：读写分离 - 数据库框架版本 2.0

解答：使用 ShardingSphere-jdbc 5.0.0-alpha 实现读写分离配置。

第一步：选择配置方式，采用SPRING BOOT STARTER 配置

```yaml
spring:
  shardingsphere:
    props:
      # 显示具体sql查询情况
      sql-show: true
    datasource:
      names: master-ds,slave-ds
      # 通用配置
      common:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: willow   ##这里不太明白，难道主从库的账号密码是一定一样吗？
        password: 123456
      # 主库数据源
      master-ds:
        jdbc-url: jdbc:mysql://localhost:3306/java1?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=true
      # 从库数据源
      slave-ds:
        jdbc-url: jdbc:mysql://localhost:3306/java2?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=true
    rules:
      # 读写分离配置
      replica-query:
        dataSources:
          # 逻辑数据源
          pr_ds:
            # 指定主库
            primary-data-source-name: master-ds
            # 指定从库
            replica-data-source-names: slave-ds
            # 负载均衡策略，名字为自定义，若不填，在 Spring 2.x 下会报空指针异常
            load-balancer-name: round-robin
        load-balancers:
          # 负载均衡策略名
          round-robin:
            # 轮询策略
            type: ROUND_ROBIN
            # 无需设置，但为避免空指针异常，进行了任意设置
            props:
              workid: 123
```

如果使用这种方式：需要添加对应的starter的依赖，参考

[官方文档]: https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/usage/sharding/spring-boot-starter/

```
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
    <version>${shardingsphere.version}</version>
</dependency>
```

第二步：使用，使用就直接使用jdbcTemplate就行了。

```
@Component
public class UserManagerImpl implements UserManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<User> listUserSharding() {
        return jdbcTemplate.query("select * from user", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int insertSharding(String account, String name) {
        return jdbcTemplate.update("insert into user(account,name) values (?,?)",account,name);
    }
}

```

shardingSphere 会自动解析sql帮我们选择主从库的。

```java
2021-03-15 15:37:52.694  INFO 15768 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Logic SQL: select * from user
2021-03-15 15:37:52.695  INFO 15768 --- [nio-8080-exec-1] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(limit=Optional.empty, lock=Optional.empty)
2021-03-15 15:37:52.697  INFO 15768 --- [nio-8080-exec-1] ShardingSphere-SQL                       : Actual SQL: slave-ds ::: select * from user
```

