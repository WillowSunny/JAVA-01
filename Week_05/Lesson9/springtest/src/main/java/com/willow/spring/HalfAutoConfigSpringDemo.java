package com.willow.spring;


import com.willow.spring.config.HalfAutoConfigTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HalfAutoConfigSpringDemo {
//    @Autowired
//    HalfAutoConfigTest halfAutoConfigTest;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("scanApplicationContext.xml");
        HalfAutoConfigTest halfAutoConfigTest = (HalfAutoConfigTest)context.getBean("halfAutoConfigTest");
        halfAutoConfigTest.say();
    }

//    public void say(){
//        halfAutoConfigTest.say();
//    }
}
