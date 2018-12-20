package com.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ServiceDemo {

    public static void main(String[] args) throws Exception {

        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        String path = "/zk-3503-storybox";
        zkClient.createPersistent(path, true);
        System.out.println("success create znode.");

        //System.out.println(zkClient.readData(path));
        zkClient.writeData(path, "45612300011111110000000099");
        Thread.sleep(1000);
        //zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);



//
//        String path = "/zk-book";
//        ZkClient zkClient = new ZkClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000);
////        zkClient.createEphemeral(path, "123");
//
//        zkClient.subscribeDataChanges(path, new IZkDataListener() {
//            public void handleDataDeleted(String dataPath) throws Exception {
//                System.out.println("Node " + dataPath + " deleted.");
//            }
//            public void handleDataChange(String dataPath, Object data) throws Exception {
//                System.out.println("Node " + dataPath + " changed, new data: " + data);
//            }
//        });
////
//        System.out.println(zkClient.readData(path));
//        zkClient.writeData(path, "456");
//        Thread.sleep(1000);
//        zkClient.delete(path);
//        Thread.sleep(Integer.MAX_VALUE);
    }

}
