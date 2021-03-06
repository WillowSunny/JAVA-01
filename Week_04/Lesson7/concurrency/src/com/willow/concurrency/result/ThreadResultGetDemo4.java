package com.willow.concurrency.result;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 4、利用sleep 方法，他是抱锁等待，等到一定时间就会自动变成Runnable态，接着执行。
 * 这个就不太友好了，因为你不知道sleep的时间需要多长。
 */
public class ThreadResultGetDemo4 {
    private static int calculateResult;
    private static Object oo = new Object();
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        Thread thread1 = new Thread(() -> {
            calculateResult = sum(); //这是得到的返回值
            System.out.println("子线程里打印的："+calculateResult);
            System.out.println("开始唤醒main线程：");
        });
        thread1.setName("计算线程：");
        // 异步执行 下面方法
        thread1.start();
        // 确保  拿到result 并输出
        Thread.sleep(1000L);
        System.out.println("异步计算结果为："+calculateResult);
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

