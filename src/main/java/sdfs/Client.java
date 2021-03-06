package main.java.sdfs;

import main.java.sdfs.namenode.DirNode;
import main.java.sdfs.namenode.Entity;
import main.java.sdfs.namenode.FileNode;
import main.java.sdfs.namenode.Master;
import main.java.sdfs.persistent.SDFS_Save;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;

/**
 * Created by HaoqiWu on 10/13/17.
 */
public class Client implements ISimpleDistributedFileSystem{
    Master master = new Master();
    byte[] tmp = new byte[20];

    public void testFile(){
        test(master.root);
    }
    public void test(Entity root){
        Entity entity = root;
        if (entity.getType() == Entity.TYPE.DIR){
            for (int i = 0; i < ((DirNode)root).getContents().size(); i++) {
                if (((DirNode)root).getContents().get(i).getType() == Entity.TYPE.DIR){
                    test(((DirNode)root).getContents().get(i));
                }else{
                    System.out.println(((DirNode)root).getContents().get(i).getName());
                }
            }
        }else{
            System.out.println(entity.getName());
        }

    }
    @Override
    public SDFSInputStream open(String fileUri) throws FileNotFoundException, IOException {

        //todo 解析fileUri, sdfs://[ip]:[port]/foo/bar.data

        //传到Master找到对应文件，返回的是FileNode
        SDFSInputStream sdfsInputStream = null;
        try{
            FileNode fileNode = master.open(fileUri);
            if (fileNode == null) throw new IOException("File not exist!");
            sdfsInputStream = new SDFSInputStream(fileNode);
//            System.out.println(fileNode.blockNum());
            return sdfsInputStream;
        }catch (URISyntaxException e){
            e.printStackTrace();
        }catch (FileNotFoundException fe){
            System.out.println("File Not Found!");
        }
        return null;


    }

    @Override
    public SDFSOutputStream create(String fileUri) throws FileAlreadyExistsException, IOException {
        try{
            FileNode fileNode = master.open(fileUri);
//            System.out.println("Create: "+fileNode.getName());
//            fileNode.blockNum();
            //如果文件已经存在，抛出异常
            if (fileNode != null){
                throw new FileAlreadyExistsException("File already exists!");
            }
            //如果文件还未存在，则创建出一个新的fileNode，并new一个SDFSOutputStream,并返回
            //todo:详细写出master中的create方法，记得创建出一个新的filenode并初始化，同时在对应的DirNode下添加信息
            fileNode = master.create(fileUri);
            System.out.println("Create File: "+fileNode.getName());
            SDFSOutputStream sdfsOutputStream = new SDFSOutputStream(fileNode);
            return sdfsOutputStream;
        }catch (URISyntaxException ue){
            ue.printStackTrace();
        }
        return null;
    }

    @Override
    public void mkdir(String fileUri) throws FileAlreadyExistsException, IOException {
        try{
            master.mkdir(fileUri);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void save() throws IOException{
        SDFS_Save sdfs_save = new SDFS_Save(master.root);
        sdfs_save.save("root");
//        System.out.println(master.root.getId());
        saveFile(master.root);
    }
    private void saveFile(DirNode root) throws IOException{
        for (int i = 0; i < root.getContents().size(); i++) {
            Entity entity = root.getContents().get(i);
            if (entity.getType() == Entity.TYPE.DIR){
                saveFile((DirNode) entity);
            }else{
                SDFS_Save sdfs_save = new SDFS_Save((FileNode)entity);
                sdfs_save.save(entity.getName());
            }
        }
    }
}
