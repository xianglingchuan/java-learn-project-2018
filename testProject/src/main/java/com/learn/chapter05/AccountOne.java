package com.learn.chapter05;

import java.util.ArrayList;
import java.util.List;

//破坏占用且等待条件
class Allocator{
    private List<Object> als = new ArrayList<>();

    //一次性申请所有资源
    synchronized  boolean apply(Object from, Object to){
        if(als.contains( from ) || als.contains( to )){
            return false;
        }else{
            als.add( from );
            als.add( to );
        }
        return true;
    }

    //归还资源
    synchronized  void free(Object from, Object to){
        als.remove( from );
        als.remove( to );
    }
}



public class AccountOne {

    //artr 应该为单例
    private Allocator actr;

    private  int balance;

    //转帐
    void transfer(AccountOne target, int amt){
        //一次性申请转出帐户和转入帐户，直到成功
        while (!actr.apply( this, target ))
            ;
        try{
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
        }finally {
            actr.free( this, target);
        }
    }



}
