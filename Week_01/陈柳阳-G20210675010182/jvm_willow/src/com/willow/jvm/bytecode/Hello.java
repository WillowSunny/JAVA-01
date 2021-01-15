package com.willow.jvm.bytecode;

/**
 * <p>Description:</p>
 * Create by willow on 2021/1/15 at 16:49
 */
public class Hello {
    public static void main(String[] args) {
        A ab = new B();
        ab = new B();
        ab = new A();
    }
}
