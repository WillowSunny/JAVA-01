package com.willow.datasource.dao;

import com.willow.datasource.dao.bean.User;

import java.util.List;

public interface UserManager {


    List<User> listUser() ;

    int insert(String account,String name);

}
