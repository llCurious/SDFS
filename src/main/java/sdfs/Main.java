package main.java.sdfs;

import main.java.sdfs.datanode.DataNode;

/**
 * Created by HaoqiWu on 10/14/17.
 */
public class Main {
    public static void main(String[] args) {
        byte[] b = new byte[20];
        try{
            DataNode dn = new DataNode();
            dn.read(1,0,20,b);
//            for (int i = 0; i < b.length; i++) {
//                System.out.println(b[i]);
//            }
            Server server = new Server();
            server.open("/data/test.txt");
            server.create("/data/test/asdasd.txt");
            server.open("/data/test/asdasd.txt");
//            server.mkdir("/data/wow");
//            server.open("/data/wow");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
