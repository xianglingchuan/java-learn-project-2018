//package com;
//
//import java.io.IOException;
//import java.util.StringTokenizer;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
////import org.smartloli.game.x.m.ubas.util.SystemConfig;
//
//
//public class WordCount {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(WordCount.class);
//
//    private static Configuration conf;
//
//    /**
//     * 设置高可用集群连接信息
//     */
//    static {
//        //String hdfsHost = "nna:9000,nns:9000";
//        //String[] hosts = SystemConfig.getPropertyArray("game.x.hdfs.host", ",");
//        //game.x.hdfs.host=nna:9000,nns:9000
//
//        String hdfsHost = "127.0.0.1:9000";
//
//
//        //单机版本直接使用默认即可
//        conf = new Configuration();
//        conf.set("fs.defaultFS", "hdfs://localhost:9000");
//        //conf.set("dfs.nameservices", "cluster1");		// 指定hdfs的nameservice为cluster1
//        //conf.set("dfs.ha.namenodes.cluster1", "nna,nns");	// cluster1下面有两个NameNode，分别是nna节点，nns节点
//
//        //conf.set("dfs.namenode.rpc-address.cluster1.nna", hosts[0]);	// nna节点的RPC通信地址
//        //conf.set("dfs.namenode.rpc-address.cluster1.nns", hosts[1]);	// nns节点的RPC通信地址
//
//
//        // 配置失败自动切换实现方式
//        //conf.set("dfs.client.failover.proxy.provider.cluster1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
//
//        // 打包到运行集群运行
//        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
//        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
//    }
//
//    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
//
//        private final static IntWritable one = new IntWritable(1);
//        private Text word = new Text();
//
//        /**
//         * 源文件：a b b
//         *
//         * map之后：
//         *
//         * a 1
//         *
//         * b 1
//         *
//         * b 1
//         */
//        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
//            StringTokenizer itr = new StringTokenizer(value.toString());	// 整行读取
//            while (itr.hasMoreTokens()) {
//                word.set(itr.nextToken());		// 按空格分割单词
//                System.out.println( "word====>"+word.toString() );
//                context.write(word, one);		// 每次统计出来的单词+1
//            }
//        }
//    }
//
//    /**
//     * reduce之前：
//     *
//     * a 1
//     *
//     * b 1
//     *
//     * b 1
//     *
//     * reduce之后:
//     *
//     * a 1
//     *
//     * b 2
//     */
//    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
//        private IntWritable result = new IntWritable();
//
//        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
//            int sum = 0;
//            for (IntWritable val : values) {
//                sum += val.get();		// 分组累加
//            }
//            result.set(sum);
//            context.write(key, result);	// 按相同的key输出
//        }
//    }
//
////    public static void main(String[] args) {
////        try {
////            if (args.length < 1) {
////                logger.info("args length is 0");
////                run("test");
////            } else {
////                run(args[0]);
////            }
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
////    }
//
//    private static void run(String name) throws Exception {
//        Job job = Job.getInstance(conf);	// 创建一个任务提交对象
//        job.setJarByClass(WordCount.class);
//        job.setMapperClass(TokenizerMapper.class);	// 指定Map计算的类
//        job.setCombinerClass(IntSumReducer.class);	// 合并的类
//        job.setReducerClass(IntSumReducer.class);	// Reduce的类
//        job.setOutputKeyClass(Text.class);			// 输出Key类型
//        job.setOutputValueClass(IntWritable.class);	// 输出值类型
//
//
////        game.x.hdfs.input.path=/tmp/test2/%s
////        game.x.hdfs.output.path=/tmp/res/v10/%s
////        game.x.hdfs.standlone.input.path=hdfs://nns:9000/tmp/test2/%s
////        game.x.hdfs.standlone.output.path=hdfs://nns:9000/tmp/res/v9/%s
//
//
////        String inputPath = "/tmp/test2/%s";
////        String outputPath = "/tmp/res/v10/%s";
//
//        String inputPath = "/test2/%s";
//        String outputPath = "/test2/res/%s";
//
//
//        // 设置统计文件在分布式文件系统中的路径
//        //String tmpLocalIn = SystemConfig.getProperty("game.x.hdfs.input.path");
//        String tmpLocalIn = inputPath;
//
//        String inPath = String.format(tmpLocalIn, name);
//        // 设置输出结果在分布式文件系统中的路径
//
//        //String tmpLocalOut = SystemConfig.getProperty("game.x.hdfs.output.path");
//        String tmpLocalOut = outputPath;
//
//        String outPath = String.format(tmpLocalOut, name);
//
//        FileInputFormat.addInputPath(job, new Path(inPath));	// 指定输入路径
//        FileOutputFormat.setOutputPath(job, new Path(outPath));	// 指定输出路径
//
//        int status = job.waitForCompletion(true) ? 0 : 1;
//
//        System.exit(status);										// 执行完MR任务后退出应用
//    }
//}
