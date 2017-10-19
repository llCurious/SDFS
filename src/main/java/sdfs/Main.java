package main.java.sdfs;

import main.java.sdfs.datanode.DataNode;

/**
 * Created by HaoqiWu on 10/14/17.
 * 测试SDFS的main方法，程序入口
 */
public class Main {
    public static void main(String[] args) {
        byte[] b = new byte[20];
        try{
            System.out.println("\n_________________________测试DataNode的读取");
            DataNode dn = new DataNode();
            dn.read(1,0,20,b);
//            for (int i = 0; i < b.length; i++) {
//                System.out.println(b[i]);
//            }

            System.out.println("\n_________________________测试文件和目录的创建");
            Server server = new Server();
//            server.create("/sdasd.sss");
//            server.mkdir("/data");
//            server.create("/data/test.txt");
//
//            server.mkdir("/data/test");
//            server.open("/data/test.txt");
//            server.create("/data/test/asdasd.txt");
//            server.create("/data/test/2.txt");
//            server.create("/data/test/3.txt");
//            //测试错误的Uri
////            server.create("/data/testww/4.txt");
//            server.open("/data/test/asdasd.txt");
//            server.mkdir("/data/wow");
//            server.create("/data/wow/5.txt");
            server.open("/data/test.txt");
            System.out.println("\n_________________________TEST,输出所有的文件");
//            SDFS_Load sdfs_load = new SDFS_Load("src/test.xml");
            server.testFile();
//            server.create("/add.tet");

            server.save();
//            server.open("/data/wow");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
