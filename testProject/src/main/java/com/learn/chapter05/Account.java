package com.learn.chapter05;

public class Account {

    private int balance;


    //转帐
    void transfer(Account target, int amt){
        //锁定转出帐户
        synchronized (this){
            //锁定转入帐户
            synchronized (target){
                if(this.balance > amt){
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }

    }
}
