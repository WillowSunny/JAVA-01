package com.willow.datasource.dao.impl;

import com.willow.datasource.dao.UserManager;
import com.willow.datasource.dao.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
