package com.willow.concurrency;

public class DaemonDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("中断唤醒");
            }
            Thread t = Thread.currentThread();
            System.out.println("当前线程："+t.getName());
        };
        Thread thread = new Thread(task);
        thread.setName("test-daemon-1");
        thread.setDaemon(false);
        thread.start();
        thread.interrupt();

    }
}
