package com.learn.chapter03;

public class SafeCalcTwo {

    static  long value = 0l;
    synchronized  long get(){
        return value;
    }

    synchronized static  void addOne(){
        value += 1;
    }
}
