package com.service;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


//检测节点是否存在 - 同步方法

public class Exist_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 5000, //
                new Exist_API_Sync_Usage());
        connectedSemaphore.await();

        Stat stat = zk.exists(path, true);
        if(stat!=null){
            System.out.println( "节点已经存在."+stat.toString() );
        }else{
            //创建节点
            zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            //设备内容
            zk.setData(path, "123".getBytes(), -1);

            //创建子节点
            zk.create(path + "/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("success create znode: " + path + "/c1");

            //删除子节点
            //zk.delete(path + "/c1", -1);
            //删除主节点
            //zk.delete(path, -1);

            Thread.sleep(Integer.MAX_VALUE);

        }
    }

    public void process(WatchedEvent event) {
        try {
            if (KeeperState.SyncConnected == event.getState()) {
                if (EventType.None == event.getType() && null == event.getPath()) {
                    connectedSemaphore.countDown();
                } else if (EventType.NodeCreated == event.getType()) {
                    System.out.println("success create znode: " + event.getPath());
                    zk.exists(event.getPath(), true);
                } else if (EventType.NodeDeleted == event.getType()) {
                    System.out.println("success delete znode: " + event.getPath());
                    zk.exists(event.getPath(), true);
                } else if (EventType.NodeDataChanged == event.getType()) {
                    System.out.println("data changed of znode: " + event.getPath());
                    zk.exists(event.getPath(), true);
                }
            }
        } catch (Exception e) {
        }
    }
}
