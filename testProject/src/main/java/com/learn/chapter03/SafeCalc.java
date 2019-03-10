package com.learn.chapter03;

public class SafeCalc {

    long value = 0l;
    synchronized  long get(){
        return value;
    }

    synchronized  void addOne(){
        value += 1;
    }
}
