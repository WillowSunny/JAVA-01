package com.willow.jdbc.test.demo.jdbc;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class HikariProcess {
    @Resource
    DataSource dataSource;
    public void executeInsert() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "insert into tt_student(name,code) values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"willow34");
        preparedStatement.setString(2,"1004");
        int i = preparedStatement.executeUpdate();

    }
}
