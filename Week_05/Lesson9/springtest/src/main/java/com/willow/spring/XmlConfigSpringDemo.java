package com.willow.spring;


import com.willow.spring.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigSpringDemo {
//    @Autowired
//    HalfAutoConfigTest halfAutoConfigTest;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("xmlApplicationContext.xml");
        Student student = (Student)context.getBean("student123");
        student.say();
    }

//    public void say(){
//        halfAutoConfigTest.say();
//    }
}
