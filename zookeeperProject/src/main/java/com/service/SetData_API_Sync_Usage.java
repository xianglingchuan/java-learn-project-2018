package com.service;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 *
 * 更新数据节点 - 同步方式
 *
 *
 * */

public class SetData_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 5000, new SetData_API_Sync_Usage());
        connectedSemaphore.await();

        //创建节点及数据
        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path);
        zk.getData(path, true, null);

        //设置节点内容
        Stat stat = zk.setData(path, "456".getBytes(), -1);
        System.out.println("czxID: " + stat.getCzxid() + ", mzxID: " + stat.getMzxid() + ", version: " + stat.getVersion());
        Stat stat2 = zk.setData(path, "456".getBytes(), stat.getVersion());
        System.out.println("czxID: " + stat2.getCzxid() + ", mzxID: " + stat2.getMzxid() + ", version: " + stat2.getVersion());

        //设备节点数据
        try {
            zk.setData(path, "456".getBytes(), stat.getVersion());
            //zk.setData(path, "456".getBytes(), -1);
        } catch (KeeperException e) {
            System.out.println("Error: " + e.code() + "," + e.getMessage());
        }
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
