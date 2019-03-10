package com.learn.chapter01;


//多核场景下的可见性问题
public class TestOne {

    private long count = 0;

    private void add10k(){
        int idx = 0;
        while(idx++ < 10000){
            count +=1;
        }
    }

    public static long calc(){
        final TestOne test01 = new TestOne();
        //创建两个线程，执行add()操作
        Thread th1 = new Thread(()->{
            test01.add10k();
        });

        Thread th2 = new Thread(()->{
            test01.add10k();
        });

        //启动两个线程
        th1.start();
        th2.start();

        //等待两个线程执行结束
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return test01.count;
    }

    public static void main(String[] args){
        long l = calc();
        System.out.print( "l=====>"+l);
    }
}




