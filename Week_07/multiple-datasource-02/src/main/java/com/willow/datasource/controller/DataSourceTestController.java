package com.willow.datasource.controller;

import com.willow.datasource.dao.UserManager;
import com.willow.datasource.dao.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataSourceTestController {

    @Autowired
    private UserManager userManager;

    @GetMapping("/masterInsertSharding")
    public String masterInsertSharding(@RequestParam(value = "account", defaultValue
            = "World") String account,@RequestParam(value = "name", defaultValue = "World") String name){
        userManager.insertSharding(account, name);
        return String.format("Hello %s!", name);
    }

    @GetMapping("/slaveQuerySharding")
    public String slaveQuerySharding(@RequestParam(value = "account", defaultValue = "World") String account){
        List<User> users = userManager.listUserSharding();
        return String.format("Hello %s!", users.get(0).getName());
    }
}
