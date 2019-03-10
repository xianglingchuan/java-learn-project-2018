package com.learn.chapter04;

public class AccountTwo {

    private int balance;

    //转帐
    synchronized  void transfer(AccountTwo target, int amt){

        if(this.balance > amt){
            this.balance -= amt;
            target.balance += amt;

        }

    }

}
