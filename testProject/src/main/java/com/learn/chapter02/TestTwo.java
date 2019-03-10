package com.learn.chapter02;

public class TestTwo {

    int var = 100;

    public void executeSmallThread(){
        //线程start()规则
        Thread B = new Thread( ()->{
            //主线程调用B.start()之前
            //所有对共享变量的修改，此处皆可见
            //此例中, var =77
            System.out.print( "var=====>"+this.var);
        } );
        //此处对共享变量var修改
        var = 77;
        //主线程启动子线程
        B.start();
    }

    public static void main(String[] args) {

        TestTwo testOne = new TestTwo();

        //创建两个线程，执行add()操作
        Thread th1 = new Thread( () -> {
            testOne.executeSmallThread();
        } );
        //启动线程
        th1.start();
        //等待线程执行结束
        try {
            th1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
