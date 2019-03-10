package com.learn.chapter04;

public class Account {

    //锁: 保护帐户余额
    private  final Object balLock = new Object();
    //帐户余额
    private  Integer balance;

    //锁:保护帐号密码
    private final Object pwLock = new Object();
    //帐户密码
    private String password;

    //取款
    void withdraw(Integer amt){
        synchronized (balLock){
            if(this.balance > amt){
                this.balance -= amt;
            }
        }
    }

    //查看余额
    Integer getBalance(){
        synchronized (balLock){
            return this.balance;
        }
    }

    //修改密码
    void updatePassword(String pw){
        synchronized (pwLock){
            this.password = pw;
        }
    }

    //查看密码
    String getPassword(){
        synchronized (pwLock){
            return this.password;
        }
    }

}












