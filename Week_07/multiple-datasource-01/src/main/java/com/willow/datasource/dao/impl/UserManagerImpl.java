package com.willow.datasource.dao.impl;

import com.willow.datasource.dao.UserManager;
import com.willow.datasource.dao.bean.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
