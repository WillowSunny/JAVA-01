package com.willow.test.spring.boot.controller;

import com.willow.service.spring.boot.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceTestController {

    @Autowired(required=false)
    private ServiceConfiguration serviceConfiguration;

    @RequestMapping("/say")
    public String say(){
        System.out.println(serviceConfiguration.getName()+"说：你个损色！");
        return serviceConfiguration.getName()+"说：你个损色！";
    }
}
