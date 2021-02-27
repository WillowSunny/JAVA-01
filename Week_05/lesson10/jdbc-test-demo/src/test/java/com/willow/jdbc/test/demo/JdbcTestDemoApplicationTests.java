package com.willow.jdbc.test.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@SpringBootTest
class JdbcTestDemoApplicationTests {
	@Resource
	DataSource dataSource;

	@Test
	void contextLoads() throws SQLException {
		Connection connection = dataSource.getConnection();
		DatabaseMetaData metaData = connection.getMetaData();
		System.out.println("数据源》》》》"+dataSource.getClass());
		System.out.println("链接》》》》》"+ connection);
		System.out.println("用户名》》》》"+ metaData.getUserName());
	}

}
