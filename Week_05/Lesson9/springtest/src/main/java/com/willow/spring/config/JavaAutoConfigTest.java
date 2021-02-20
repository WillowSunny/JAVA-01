package com.willow.spring.config;

import com.willow.spring.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaAutoConfigTest {
    @Bean(name = "student",initMethod = "init")
    public Student createStudent(){
        System.out.println("java config配置，创建Student实例。。。。。。。");
        return new Student("1001","willow");
    }
}
