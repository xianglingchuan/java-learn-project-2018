package com.service;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class ServerDemo implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);


    public static void main(String[] args)  throws Exception {

        ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000, new Zookeeper_Create_API_Sync_Usage());
        System.out.println(zookeeper.getState());
        connectedSemaphore.await();

        //创建持久节点
        String path3 = zookeeper.create("/zk-3503-sorybox", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Success create znode: " + path3);


        //发送消息


    }

    public void process(WatchedEvent event) {
        if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }

}
