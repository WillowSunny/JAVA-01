package com.willow.jdbc.test.demo.jdbc;

import com.willow.jdbc.test.demo.jdbc.MySqlExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class StatementProcess {

    @Autowired
    private MySqlExecutor mySqlExecutor;

    public void doProcess() throws SQLException, ClassNotFoundException {
        mySqlExecutor.executeQuerySql("SELECT id, name, code FROM tt_student");
        mySqlExecutor.executeUpdateSql("update tt_student set name='willow2' where id=2");
        mySqlExecutor.executeInsertSql("insert into tt_student(name,code) values ('liuyang','1003')");
        mySqlExecutor.executeDeleteSql("delete from  tt_student  where id=2");
    }
}
