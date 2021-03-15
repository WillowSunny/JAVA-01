package com.willow.datasource.controller;

import com.willow.datasource.dao.UserManager;
import com.willow.datasource.dao.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

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
