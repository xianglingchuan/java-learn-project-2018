package com.zkclient;

import java.net.URISyntaxException;

public class BasePath {

    public static void main(String[] args) throws URISyntaxException {
        //1、来获得当前线程的ClassLoader相应的资源路径
        String url = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
        System.out.println("url:"+url);
        //2、指定类的ClassLoader相应的资源路径
        String url2 = BasePath.class.getClassLoader().getResource("").toURI().getPath();
        System.out.println("url2:"+url2);

        String url3 = ClassLoader.getSystemResource("").toURI().getPath();
        System.out.println("url3:"+url3);
        //4、当前类的详细地址，包含路径包名
        String url4 = BasePath.class.getResource("").toURI().getPath();
        System.out.println("url4:"+url4);
        //5、当前类的根目录路径
        String url5 = BasePath.class.getResource("/").toURI().getPath();
        System.out.println("url5:"+url5);
        //6、用户的当前工作目录(user.dir)
        String url6 = System.getProperty("user.dir");
        System.out.println("url6:"+url6);

        //7、test.txt文件的路径为 项目名\src\test.txt;类TestPath所在包的第一级目录位于src目录下
        String url7 = BasePath.class.getClassLoader().getResource("test.xml").toURI().getPath();
        System.out.println("url7:"+url7);
    }
}
