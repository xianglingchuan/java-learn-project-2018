package com.learn.chapter04;

public class AccountThree {

    private Object lock;

    private int balance;

    private AccountThree(){}

    //创建Account时传入同一个lock对象
    public AccountThree(Object lock){
        this.lock = lock;
    }

    //转帐
    void transfer(AccountThree target, int amt){
        //此处检查所有对象共享的锁
        synchronized (lock){
            if(this.balance > amt){
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }



}
