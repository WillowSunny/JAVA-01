package com.willow.concurrency.result;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 5、采用Future和Callable搭配使用，这里，callable只能是用线程池的方式提交任务，
 * 因此此次的demo中创建了一个单线程的线程池。
 */
public class ThreadResultGetDemo5 {
    // 在这里创建一个线程或线程池，
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        ThreadResultGetDemo5 resultGetDemo5 = new ThreadResultGetDemo5();
        // 异步执行 下面方法
        Future<Integer> future = resultGetDemo5.sumTask();
        // 确保  拿到result 并输出
        if(!future.isCancelled()){
            try {
                Integer result = future.get(1, TimeUnit.SECONDS);
                System.out.println("异步计算结果为："+result);
                System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
            } catch (ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        if(!future.isCancelled() && future.isDone()){
            System.out.println("任务结束了。");
        }
        // 然后退出main线程
        if (!resultGetDemo5.executorService.isShutdown()) {
            System.out.println("线程池还没关闭：");
        }
        System.out.println("开始关闭线程池");
        resultGetDemo5.executorService.shutdown();
    }

    public Future<Integer> sumTask(){
        return executorService.submit(() -> sum());
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

