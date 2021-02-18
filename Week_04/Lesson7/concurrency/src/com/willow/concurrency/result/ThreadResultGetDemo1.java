package com.willow.concurrency.result;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 1、利用wait方法，这里synchronized的对象是子线程对象，当子线程执行完后，JVM会将阻塞在
 * 当前子线程对象（意思是：synchronized的隐式锁是子线程对象）的线程（当前例子就是main线程）唤醒，
 * 因此不需要在代码里显式notify
 */
public class ThreadResultGetDemo1 {
    private static int calculateResult;
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        Thread thread1 = new Thread(() -> {
            calculateResult = sum(); //这是得到的返回值
            System.out.println("子线程里打印的："+calculateResult);
        });
        thread1.setName("计算线程：");
        // 异步执行 下面方法
        thread1.start();
        // 确保  拿到result 并输出
        synchronized (thread1){
            try {
                thread1.wait();
                System.out.println("主线程等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (thread1){
            System.out.println("异步计算结果为："+calculateResult);
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}

