package com.service;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;



/**
 *
 * 创建节点 - 同步方式
 *
 *
 * */

public class Zookeeper_Create_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        //127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
        ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000, new Zookeeper_Create_API_Sync_Usage());
        System.out.println(zookeeper.getState());
        connectedSemaphore.await();


        /**
         *
         * 节点类型，类型定义在枚举CreateMode中：
         *
         *（1）PERSISTENT：持久；
         *（2）PERSISTENT_SEQUENTIAL：持久顺序；
         *（3）EPHEMERAL：临时；
         *（4）EPHEMERAL_SEQUENTIAL：临时顺序。
         *
         */




        //创建一个临时节点
        String path1 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);

        //创建一个临时顺序节点
        String path2 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + path2);

        //创建持久节点
        String path3 = zookeeper.create("/zk-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Success create znode: " + path3);

        //创建持久顺序节点
        String path4 = zookeeper.create("/zk-ephemeral-", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("Success create znode: " + path4);


    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}
