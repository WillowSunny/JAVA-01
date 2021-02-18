package com.willow.concurrency.join;

public class JoinDemo {
    public static void main(String[] args) {
        Object oo = new Object();
        JoinTestThread thread1 = new JoinTestThread("thread1 -- ");
        //oo = thread1;
        thread1.setOo(oo);
        thread1.start();

        synchronized (thread1) {  // 这里用oo或thread1/this
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        thread1.wait(0);
//                        thread1.join();
                        System.out.println("主线程等待结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }
}

class JoinTestThread extends Thread{
    private String name;
    private Object oo;

    public void setOo(Object oo) {
        this.oo = oo;
    }

    public JoinTestThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i=0; i<100; i++){
            System.out.println(name + i);
        }
//        synchronized (oo){
//            for (int i=0; i<100; i++){
//                System.out.println(name + i);
//            }
//            oo.notify();
//        }
    }
}
