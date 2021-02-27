package com.willow.jdbc.test.demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    public static Connection connection = null;

//    public static final Connection connection =getConnection();
//String jdbcDriver,String jdbcUrl,String jdbcUser,String jdbcPassword
    public static Connection getConnection(String jdbcDriver,String jdbcUrl,String jdbcUser,String jdbcPassword) throws ClassNotFoundException, SQLException {
        if(connection != null){
            return connection;
        }
        Class.forName(jdbcDriver);
        connection = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPassword);
        return connection;
    }
}
