package com.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 *
 *  创建会话
 *
 */

public class Create_Session_Sample {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //创建client1
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000, retryPolicy);
        client.start();
        System.out.println("Zookeeper session1 established. ");

        //创建client2
        CuratorFramework client1 = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000).retryPolicy(retryPolicy).namespace("base").build();
        client1.start();

        System.out.println("Zookeeper session2 established. ");
    }

}
