package com.service;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 *
 * 删除节点 - 同步方式
 *
 *
 * */

public class Delete_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {

        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 5000,new Delete_API_Sync_Usage());
        connectedSemaphore.await();

        //创建正式节点
        zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path);

        //创建正式子节点
        zk.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path + "/c1");


        //删除父节点这里会报错
        try {
            zk.delete(path, -1);
        } catch (Exception e) {
            System.out.println("fail to delete znode: " + path);
        }

        //先删除子节点
        zk.delete(path + "/c1", -1);
        System.out.println("success delete znode: " + path + "/c1");

        //在删除父节点
        zk.delete(path, -1);
        System.out.println("success delete znode: " + path);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
        }
    }
}

