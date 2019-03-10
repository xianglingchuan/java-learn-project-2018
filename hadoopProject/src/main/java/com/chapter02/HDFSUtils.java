//package com.chapter02;
//
//import org.apache.hadoop.conf.Configuration;
//
//import org.apache.hadoop.fs.*;
//
//import java.io.IOException;
//
//
//public class HDFSUtils {
//
//    private static Configuration conf = null;// 申明配置属性值对象
//
//    static {
//        conf = new Configuration();
//        // 指定hdfs的nameservice为cluster1,是NameNode的URI
//        conf.set("fs.defaultFS", "hdfs://localhost:9000");
//        // 指定hdfs的nameservice为cluster1
//        //conf.set("dfs.nameservices", "cluster1");
//        // cluster1下面有两个NameNode，分别是nna节点和nns节点
//        //conf.set("dfs.ha.namenodes.cluster1", "nna,nns");
//        // nna节点下的RPC通信地址
//        //conf.set("dfs.namenode.rpc-address.cluster1.nna", "nna:9000");
//        // nns节点下的RPC通信地址
//        //conf.set("dfs.namenode.rpc-address.cluster1.nns", "nns:9000");
//        // 实现故障自动转移方式
//        //conf.set("dfs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
//
//    }
//
//
//    //上传文件
//    public static void put(String remotePath, String localPath) throws IOException {
//        FileSystem fs = FileSystem.get(conf);
//        Path src = new Path(localPath);  //本地文件地址
//        Path dst = new Path(remotePath); //hdfs文件目录
//        fs.copyFromLocalFile(src, dst);
//        fs.close();
//    }
//
//    //读取文件
//    public static void cat(String remotePath) throws IOException{
//        FileSystem fs = FileSystem.get(conf);
//        Path path = new Path(remotePath); //hdfs文件目录
//        if(fs.exists( path )){
//
//            FSDataInputStream is = fs.open( path ); //打开分布式文件操作对象
//            FileStatus status = fs.getFileStatus( path ); //获取文件状态
//            byte[] buffer = new byte[Integer.parseInt( String.valueOf( status.getLen() ) )];
//            is.readFully( 0, buffer );
//            is.close();
//            fs.close();
//            String s = new String( buffer );
//            System.out.println( s);
//        }
//    }
//
//
//    //下载文件
//    public static void get(String remotePath, String localPath)throws IOException{
//        FileSystem fs = FileSystem.get(conf);
//        Path src = new Path(localPath);  //本地文件地址
//        Path dst = new Path(remotePath); //hdfs文件目录
//        fs.copyToLocalFile( dst, src);
//        fs.close();
//    }
//
//
//    //删除
//    public static void rmr(String remotePath)throws IOException{
//        FileSystem fs = FileSystem.get(conf);
//        Path path = new Path(remotePath); //hdfs文件目录
//
//        fs.delete( path, true );
//        fs.close();
//    }
//
//
//    //查看目录
//    public static void ls(String remotePath)throws IOException{
//        FileSystem fs = FileSystem.get(conf);
//        Path path = new Path(remotePath); //hdfs文件目录
//
//        FileStatus[] status = fs.listStatus( path );
//        Path[] listPaths = FileUtil.stat2Paths( status );
//        for (Path p: listPaths){
//            System.out.println( "p==>"+p);
//        }
//        fs.close();
//    }
//
//
//    //创建目录
//    public static void mkdir(String remotePath)throws IOException{
//        FileSystem fs = FileSystem.get(conf);
//        Path path = new Path(remotePath); //hdfs文件目录
//
//        fs.create( path );
//        fs.close();
//    }
//
//
//
////    public static void main(String[] args)throws IOException{
////
//////        String remotePath = "/test2/hello_world_put.txt";
//////        String localPath = "/Users/xianglingchuan/Documents/work/hadoopWork/data/hello_world.txt";
//////        HDFSUtils.put(remotePath, localPath);
////
////
//////          String path = "/test2/hello_world_put.txt";
//////          cat(path);
////
////
//////        String remotePath = "/test2/hello_world_put.txt";
//////        String localPath = "/Users/xianglingchuan/Documents/work/hadoopWork/data/hello_world_copyToLocalFile.txt";
//////        HDFSUtils.get(remotePath, localPath);
////
////
//////        String remotePath = "/test2/hello_world_put.txt";
//////        HDFSUtils.rmr( remotePath );
////
////
////
////
////        String remotePath = "/xytest/createFile.txt";
////        HDFSUtils.mkdir( remotePath );
////        String remotePaths = "/xytest/";
////        HDFSUtils.ls( remotePaths );
////
////
////
////
////
////
////
////    }
//
//
//}
