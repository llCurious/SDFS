package sdfs;

import sdfs.datanode.DataNode;
import sdfs.namenode.DirNode;
import sdfs.namenode.FileNode;
import sdfs.namenode.Master;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

/**
 * Created by HaoqiWu on 10/13/17.
 */
public class Server implements ISimpleDistributedFileSystem{
    Master master = new Master();
    byte[] tmp = new byte[20];

    @Override
    public SDFSInputStream open(String fileUri) throws FileNotFoundException, IOException {

        //to-do 解析fileUri, sdfs://[ip]:[port]/foo/bar.data

        //传到Master找到对应文件，返回的是FileNode

        try{
            FileNode fileNode = master.open(fileUri);
            if (fileNode == null) throw new IOException("File not exist!");
            SDFSInputStream sdfsInputStream = new SDFSInputStream(fileNode);

            return sdfsInputStream;
        }catch (URISyntaxException e){
            e.printStackTrace();
        }catch (FileNotFoundException fe){
            fe.printStackTrace();
        }
        return null;


    }

    @Override
    public SDFSOutputStream create(String fileUri) throws FileAlreadyExistsException, IOException {
        try{
            FileNode fileNode = master.open(fileUri);
//            System.out.println("Create: "+fileNode.getName());
            //如果文件已经存在，抛出异常
            if (fileNode != null){
                throw new FileAlreadyExistsException("File already exists!");
            }
            //如果文件还未存在，则创建出一个新的fileNode，并new一个SDFSOutputStream,并返回
            //todo:详细写出master中的create方法，记得创建出一个新的filenode并初始化，同时在对应的DirNode下添加信息
            fileNode = master.create(fileUri);
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
}
