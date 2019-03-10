package com.learn.chapter02;

import java.util.SortedSet;

/*
* 什么是Java内存模型
*
* Happens-Before规则
* 1、程序的顺序性规则
* 2、volatile变量规则
* 3、传递性
* 4、管程中锁的规则
* 5、线程start()规则
* 6、线程join()规则
*
*
*
* */

//使用volatile的困惑
public class TestOne {

    int x = 0;
    volatile boolean v = false;
    public void writer(){
        x = 42;
        v = true;
    }

    public void reader(){
        if(v == true){
            System.out.print( "x====>"+x);
        }
    }

    public static void main(String[] args){


        final TestOne testOne = new TestOne();


        //创建两个线程，执行add()操作
        Thread th1 = new Thread(()->{
            testOne.writer();
        });

        Thread th2 = new Thread(()->{
            testOne.reader();
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


         //管程,在进入同步块之前，会自动加锁，而在代码块执行完会自动释放锁
         //加锁以及释放锁都是编统器帮我们实现的
         /*
         synchronized (this){ //此处自动加锁
            //x是共享变量，初始值=10
             if(this.x < 12){
                 this.x = 12;
             }
         }//此处自动解锁
         */


         //线程start()规则
         /*
         int var = 0;
         //线程start()规则
         Thread B = new Thread( ()->{
             //主线程调用B.start()之前
             //所有对共享变量的修改，此处皆可见
             //此例中, var =77
             //System.out.print( "var=====>"+var);
         } );
         //此处对共享变量var修改
         var = 77;
         //主线程启动子线程
         B.start();
         */

         //线程join()规则
         /*
         int var = 0;
         Thread B = new Thread(()->{
             //引处对共享变量var修改
             var = 66;
         });
         //例如此处对共享变量修改
         //则这个修改结果对线程B可见
         //主线程启动子线程
         B.start();
         B.join();
         //子线程所有对共享变量的修改
         //在主线程调用B.join()之后皆可见
         //此例中,var=66
         */

         //被我们忽视的final
        /*
        final int x;
         //错误的构造函数
         public void FinalFieldExample(){
             x = 3;
             y = 4;
             //此处就是讲this逸出
             global.obj = this;
         }
         */




    }
}



