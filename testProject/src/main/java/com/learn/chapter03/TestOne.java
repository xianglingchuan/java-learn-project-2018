package com.learn.chapter03;


//互斥锁(上):解决原子性问题
public class TestOne {

    //修饰非静态方法
    synchronized  void foot(){
        //临界区
    }

    //修饰静态方法
    synchronized  static void bar(){
        //临界区
    }

    //修饰代码块
    Object obj = new Object();
    void baz(){
        synchronized (obj){
            //临界区
        }
    }

    //当修饰静态方法的时候，锁定的是当前类的Class对象，在上面的例子中就是Class TestOne:
    //当修饰非静态方泽的时候，锁定的是当前实例对象this;

    //synchronized修饰静态方法
    /*
    class X{
        //修饰静态方法
        synchronized (X.class) static void bar(){
            //临界区
        }
    }
    */

    //修饰非静态方法
    /*
    class X{
        //修饰非静态方法
        synchronized (this) void foo(){
            //临界区
        }
    }
    */





}
