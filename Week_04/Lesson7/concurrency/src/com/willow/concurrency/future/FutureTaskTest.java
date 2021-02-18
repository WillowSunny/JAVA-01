package com.willow.concurrency.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String flag="123";
        FutureTask f = new FutureTask<String>(new SimpleRunnable(),flag);
        f.run();
        String s = f.get().toString();
        System.out.println("s:"+s);
    }

}

class SimpleRunnable implements Runnable{
    @Override
    public void run() {
        int i = 1/2;
        //int i = 1/0;
    }
}