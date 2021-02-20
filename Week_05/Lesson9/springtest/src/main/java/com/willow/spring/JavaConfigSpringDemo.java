package com.willow.spring;


import com.willow.spring.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JavaConfigSpringDemo {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.willow.spring");
        Student student = (Student)context.getBean("student");
        student.say();
    }

}
