package com.learn.chapter04;

public class AccountFour {


    private int balance;

    //转帐
    void transfer(AccountFour target, int amt){
        //此处检查所有对象共享的锁
        synchronized (AccountFour.class){
            if(this.balance > amt){
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }
}
