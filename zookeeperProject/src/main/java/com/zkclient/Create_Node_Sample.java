package com.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class Create_Node_Sample {
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        String path = "/zk-3503-storybox";
        zkClient.createPersistent(path, true);
        System.out.println("success create znode.");
    }
}
