package main.java.sdfs.namenode;

import main.java.sdfs.LocatedBlock;
import main.java.sdfs.persistent.SDFS_Load;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.rmi.server.ExportException;
import java.util.List;

/**
 * Created by HaoqiWu on 10/13/17.
 * 作为SDFS系统的Master节点，保存根目录的信息以及文件metadata
 * 与Client直接沟通的节点
 * 传入文件Uri，并提供多个读写操作
 */
public class Master implements INameNode {
    //todo 改回private
    public DirNode root = null;
    private int dirNum;

    public Master(){
        dirNum = 0;
        root = new DirNode(dirNum);
        root.setId(0);
        root.setName("");
        root.setType(Entity.TYPE.DIR);
        SDFS_Load sdfs_load = new SDFS_Load("src/record/root.xml");
        try{
            root = (DirNode) sdfs_load.load();
        }catch (Exception e){
            e.printStackTrace();
        }

//        FileNode fileNode = new FileNode();
//        fileNode.setName("test.txt");
//        fileNode.setType(Entity.TYPE.FILE);
//
//        DirNode dirNode = new DirNode(++dirNum);
//        dirNode.setName("test");
//        dirNode.setType(Entity.TYPE.DIR);
//
//        root.addEntity(fileNode);
//        root.addEntity(dirNode);
    }


    @Override
    public FileNode open(String fileUri) throws IOException, URISyntaxException {
        /*
        Uri 应该是这样的模式    sdfs://[ip]:[port]/foo/bar.data
        但是这里传入的只是  /foo/bar.data
        从root节点开始 树状寻找，找到对应文件
        */
        String[] names = fileUri.split("/");
//        System.out.println(names[1]);

        FileNode fileNode = (FileNode) findFile(root,names);
        if (fileNode != null){
            System.out.println("Open File: "+fileNode.getName()+fileNode.blockNum());
        }

        return fileNode;
    }

    @Override
    public FileNode create(String fileUri) throws IOException, URISyntaxException {

        //todo: 解析出 fileUri 中的 directory 和 file 的名字
        //todo: 将directory对应的 DirNode 下添加新的 Entity：fileNode
        //    /data/wow.txt
        String[] names = fileUri.split("/");
        String dirName = names[names.length-2];         //data
        String fileName = names[names.length-1];        //wow.txt

        //初始化新创建的fileNode
        FileNode fileNode = new FileNode();
        fileNode.setType(Entity.TYPE.FILE);
        fileNode.setName(fileName);
        try{
            fileNode.blockNum();

        }catch (Exception e){
            e.printStackTrace();
        }

        DirNode dirNode = (DirNode) findDir(root,names);
        if (dirNode == null) throw new IOException("Uri Error!!!!");
//        System.out.println("Dir:"+dirNode.getName());
//        System.out.println(fileNode.blockNum());
        dirNode.addEntity(fileNode);
//        System.out.println("Name: "+dirNode.getContents().get(0).getName());

        return fileNode;
    }

    @Override
    public void close(String fileUri) throws IOException, URISyntaxException {
        root = null;
    }

    @Override
    public void mkdir(String fileUri) throws IOException, URISyntaxException {
        //todo dir的ID？

        String[] names = fileUri.split("/");
        String parentName = names[names.length-2];
        String dirName = names[names.length-1];

        //创建新的DirNode
        DirNode dirNode = new DirNode(++dirNum);
        dirNode.setType(Entity.TYPE.DIR);
        dirNode.setName(dirName);

        DirNode parent = (DirNode)findDir(root, names);
        if (parent == null) throw new IOException("Uri Error!!!");
        for (int i = 0; i < parent.getContents().size(); i++) {
            if (parent.getContents().get(i).getName().equals(dirName) &&
                    parent.getContents().get(i).getType()== Entity.TYPE.DIR){
                throw new FileAlreadyExistsException("Directory already exists!");
            }
        }
        parent.addEntity(dirNode);
        System.out.println("Create Dir: "+ dirNode.getName());
//        System.out.println(parent.getContents().get(2).getName());
    }

    @Override
    public LocatedBlock addBlock(String fileUri) {

        return null;
    }

    /**
     * 从root节点开始，寻找对应名字的DirNode
     * @param root     namenode 里的 root节点
     * @param fileName     解析出pathUri的String数组
     * @return  一个文件抽象的Entity，最后返回的一定是一个DirNode，否则就是null（未找到对应的目录）
     * @throws IOException
     */
    private Entity findDir(DirNode root, String[] fileName) throws IOException{
//        System.out.println("Find DIr");
        List<Entity> contents = root.getContents();
        String name;
        boolean isFind = false;
        if (!fileName[0].equals(root.getName())){
            throw new IOException(fileName[0]+"Root Error!");
        }else if (fileName.length == 2){
            return root;
        }
        for (int i = 1; i < fileName.length; i++) {
            name = fileName[i];
//            System.out.println(name);
            for (int j = 0; j < contents.size(); j++) {
                if (contents.get(j).getName().equals(name) && contents.get(j).getType()== Entity.TYPE.DIR){
//                    System.out.println(j+contents.get(j).getName());
                    if (i < fileName.length-2){
                        contents = ((DirNode)contents.get(j)).getContents();
                        isFind = true;
                        break;
                    }else{
                        return contents.get(j);
                    }
                }
            }
            if (!isFind) return null;
            else isFind = false;
        }
        return null;
    }

    /**
     * 从root节点开始，寻找对应名字的FileNode
     * @param root      namenode里的root
     * @param fileName      解析出pathUri的String数组
     * @return 一个文件抽象的Entity，一定是一个FIleNode，否则就是null（未找到对应的文件）
     * @throws IOException
     */
    private Entity findFile(DirNode root, String[] fileName) throws IOException{
        List<Entity> contents = root.getContents();
        String name;
        boolean isFind = false;
        if (!fileName[0].equals(root.getName())){
            throw new IOException(fileName[0]+"Root Error!");
        }
        for (int i = 1; i < fileName.length; i++) {
            name = fileName[i];
            for (int j = 0; j < contents.size(); j++) {
                if (contents.get(j).getName().equals(name)){
                    if (contents.get(j).getType()== Entity.TYPE.DIR){
                        contents = ((DirNode)contents.get(j)).getContents();
                        isFind = true;
                        break;
                    }else{
                        if (i == fileName.length-1) return contents.get(j);
                        else throw new IOException("Uri Error, file cannot be a directory!");
                    }
                }
            }
            if (!isFind) return null;
            else isFind = false;
        }
        return null;
    }
}
