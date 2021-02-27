package com.willow.jdbc.test.demo.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class PrepareStatementProcess {

    @Autowired
    private MySqlExecutor mySqlExecutor;

    public void doProcess() throws SQLException, ClassNotFoundException {
        String sql = "insert into tt_student(name,code) values (?,?)";
        PreparedStatement prepareStatement = mySqlExecutor.getPrepareStatement(sql);
        prepareStatement.setString(1,"willow3");
        prepareStatement.setString(2,"1004");
        int i = prepareStatement.executeUpdate();
        System.out.println("插入数据："+i+"条。");
    }

    public void doProcessBatch() throws SQLException, ClassNotFoundException{
        String sql = "insert into tt_student(name,code) values (?,?)";
        PreparedStatement prepareStatement = mySqlExecutor.getPrepareStatement(sql);
        prepareStatement.setString(1,"willow9");
        prepareStatement.setString(2,"1004");
//        prepareStatement.addBatch();

        prepareStatement.setString(1,"willow12");
        prepareStatement.setString(2,"1004");
        prepareStatement.addBatch();
        int[] ints = prepareStatement.executeBatch();
        System.out.println("ddd");
    }
}
