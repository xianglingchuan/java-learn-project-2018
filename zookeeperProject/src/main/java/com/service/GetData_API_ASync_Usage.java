package com.service;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 *
 * 数据节点获取 - 异步方式
 *
 *
 * */

public class GetData_API_ASync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 5000,
                new GetData_API_ASync_Usage());
        connectedSemaphore.await();

        zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path);

        zk.getData(path, true, new IDataCallback(), null);

        zk.setData(path, "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == EventType.NodeDataChanged) {
                try {
                    zk.getData(event.getPath(), true, new IDataCallback(), null);
                } catch (Exception e) {
                }
            }
        }
    }
}

class IDataCallback implements AsyncCallback.DataCallback {
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println("rc: " + rc + ", path: " + path + ", data: " + new String(data));
        System.out.println("czxID: " + stat.getCzxid() + ", mzxID: " + stat.getMzxid() + ", version: " + stat.getVersion());
    }
}
