package com.willow.jdbc.test.demo.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class MySqlExecutor {
    @Value("${jdbc.driver-class-name}")
    private String jdbcDriver ;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;


    public void executeQuerySql(String sql) throws SQLException, ClassNotFoundException {
        Statement stmt = getStatement();
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String code = rs.getString("code");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 名称: " + name);
                System.out.print(", 编码: " + code);
                System.out.print("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }

        }
    }

    public void executeUpdateSql(String sql) throws SQLException, ClassNotFoundException {
        Statement stmt = getStatement();
        if(stmt == null){
            return;
        }

        try {
            int rows = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
    }

    public void executeInsertSql(String sql) throws SQLException, ClassNotFoundException {
        Statement stmt = getStatement();
        if(stmt == null){
            return;
        }

        try {
            int rows = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
    }

    public void executeDeleteSql(String sql) throws SQLException, ClassNotFoundException {
        Statement stmt = getStatement();
        if(stmt == null){
            return;
        }

        try {
            int rows = stmt.executeUpdate(sql);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
    }

    private Statement getStatement() throws SQLException, ClassNotFoundException {
        Connection conn = MyConnection.getConnection(jdbcDriver,jdbcUrl,jdbcUser,jdbcPassword);
        return conn.createStatement();
    }

    public PreparedStatement getPrepareStatement(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = MyConnection.getConnection(jdbcDriver,jdbcUrl,jdbcUser,jdbcPassword);
        return conn.prepareStatement(sql);
    }
}
