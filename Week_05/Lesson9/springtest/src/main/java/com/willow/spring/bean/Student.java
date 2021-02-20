package com.willow.spring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    String code;
    String name;

    public void init(){
        System.out.println("名字："+name);
        System.out.println("编码："+code);
        System.out.println("JavaConfig配置方式，指定的init方法");
    }
    public void say(){
        System.out.println(name+"说：这世界多么美好。。。。");
    }
}
