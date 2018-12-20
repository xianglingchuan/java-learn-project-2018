package com.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ClientDemo {

    public static void main(String[] args) throws Exception {
        String path = "/zk-3503-storybox";
        //zk1/bin/zkCli.sh -server 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        //zkClient.createEphemeral(path, "123");
        //zkClient.createPersistent(path, true);


        System.out.println("读取readData数据内容: "+zkClient.readData(path));
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
                public void handleDataDeleted(String dataPath) throws Exception {
                    System.out.println("Node " + dataPath + " deleted.");
                }
                public void handleDataChange(String dataPath, Object data) throws Exception {
                    System.out.println("Node " + dataPath + " changed, new data: " + data);
                }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

}
