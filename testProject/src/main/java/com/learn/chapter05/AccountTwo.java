package com.learn.chapter05;

//破坏不可抢占条件
public class AccountTwo {

    private int id;

    private int balance;

    //转帐
    void transfer(AccountTwo target, int amt){
        AccountTwo left = this;    //1
        AccountTwo right = target; //2
        if(this.id > target.id){   //3
            left = target;         //4
            right = this;          //5
        }                          //6
        //锁定序号小的帐户
        synchronized (left){
            //锁定序号大的帐户
            synchronized(right){
                if(this.balance > amt){
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }


}
