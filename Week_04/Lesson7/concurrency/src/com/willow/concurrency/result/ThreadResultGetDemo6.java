package com.willow.concurrency.result;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 6、采用FutureTask，他实现了RunableFuture，而RunnableFuture继承Runnable和Future，
 * 因此Future既可以交给专门的工作线程执行，也可以交给Executor实例比如线程池来执行。
 */
public class ThreadResultGetDemo6 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        FutureTask<Integer> futureTask = new FutureTask<Integer>(()->sum());
        // 异步执行 下面方法
        //第一种futuretask的方式
//        Thread thread = new Thread(futureTask);
//        thread.start();//这里是采用专门线程执行的方式
        //第二种future的方式，直接交给Executor执行
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(futureTask);
        executorService.shutdown();
        if(futureTask.isCancelled()){
            System.out.println("任务被取消了");
            return;
        }
        Integer result = futureTask.get();

        // 确保  拿到result 并输出
        System.out.println("专门线程异步计算结果为："+result);
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

