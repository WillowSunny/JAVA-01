package com.willow.concurrency.start;

public class ThreadStartDemo {

    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            Thread threadRunable = new Thread(new ThreadRunable());
            threadRunable.setName("实现Runable接口方式线程：第"+i+"个");
            threadRunable.start();
        }

        for(int i=0; i<10; i++){
            ThreadInherit threadInherit = new ThreadInherit();
            threadInherit.setName("继承方式线程：第"+i+"个");
            threadInherit.start();
        }
    }

    static class ThreadInherit extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程："+this.getName());
        }
    }

    static class ThreadRunable implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread t = Thread.currentThread();
            System.out.println("当前线程："+t.getName());
        }
    }

}
