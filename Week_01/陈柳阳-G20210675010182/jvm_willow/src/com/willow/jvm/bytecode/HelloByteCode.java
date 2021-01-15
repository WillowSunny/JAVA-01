package com.willow.jvm.bytecode;

/**
 * <p>Description:</p>
 * Create by willow on 2021/1/8 at 12:43
 */
public class HelloByteCode {
    public static final int initData = 666;

    public int compute(int aa,int bb ,int cc,int dd){
        int a = 6;
        int b = 3;
        int c= (a+b)*10*7;

        if(c == 10){
            int d = 100;
            System.out.println("C==10,D="+d);
        }else{
            int d = 101;
            System.out.println("C==10,D="+d);
        }

        String[] str = new String[]{"s1","s2","s3"};
        String strAppend ="";
        for (String s : str){
            strAppend =strAppend + s;
        }
        System.out.println(strAppend);
        StringBuilder sb = new StringBuilder();
        for (String s : str){
            sb.append(s);
        }
        System.out.println(sb.toString());
        return c;
    }

    public static void main(String[] args){
        HelloByteCode hello = new HelloByteCode();
        hello.compute(1,2,3,1);
    }
}
