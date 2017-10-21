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
            Client client = new Client();
//            client.create("/sdasd.sss");
//            client.mkdir("/data");
//            client.create("/data/test.txt");
//
//            client.mkdir("/data/test");
//            client.open("/data/test.txt");
//            client.create("/data/test/asdasd.txt");
//            client.create("/data/test/2.txt");
//            client.create("/data/test/3.txt");
//            //测试错误的Uri
////            client.create("/data/testww/4.txt");
//            client.open("/data/test/asdasd.txt");
//            client.mkdir("/data/wow");
//            client.create("/data/wow/5.txt");
            SDFSInputStream inputStream= client.open("/data/test.txt");
            inputStream.read(b);
            System.out.println("_____内容");
            for (int i = 0; i < b.length; i++) {
                System.out.println(b[i]);
            }

//            client.open("/data/test.txt");

            System.out.println("\n_________________________TEST,输出所有的文件");
//            SDFS_Load sdfs_load = new SDFS_Load("src/test.xml");
            client.testFile();
//            client.create("/add.tet");

            client.save();
//            client.open("/data/wow");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
