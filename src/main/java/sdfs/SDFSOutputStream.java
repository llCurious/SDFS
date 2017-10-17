/*
 * Copyright (c) Jipzingking 2016.
 */

package main.sdfs;

import main.sdfs.datanode.DataNode;
import main.sdfs.namenode.FileNode;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.List;

public class SDFSOutputStream implements Closeable, Flushable {
    private FileNode fileNode;
    private int start = 0;
    private final int buffer_size = 1024;
    private byte[] buffer = new byte[buffer_size];

    public SDFSOutputStream(FileNode fileNode){
        this.fileNode = fileNode;
    }
    public void write(byte[] b) throws IOException {
        //todo your code here
        if (start+b.length < buffer_size){
            System.arraycopy(b,0,buffer,start,b.length);
            start += b.length;
        }else if (start+b.length == buffer_size){
            System.arraycopy(b,0,buffer,start,b.length);
            flush();
            start = 0;
        }else{
            System.arraycopy(b,0,buffer,start,buffer_size - start);
            flush();
            start = 0;
            byte[] tmp = new byte[b.length - buffer_size + start];
            System.arraycopy(b,buffer_size - start,tmp,0,tmp.length);
            write(tmp);
        }



    }

    @Override
    public void flush() throws IOException {
        //todo your code here

        //根据返回的FileNode，找到对应的block的位置
        int blockNum = fileNode.blockNum();
        List<LocatedBlock> blocks = fileNode.getBlockInfo(0);

        //调用DataNode，传入blockNumber读取信息
        LocatedBlock block = blocks.get(0);
        DataNode dn = new DataNode();
        dn.write(block.blockNumber,0,buffer_size,buffer);
        buffer = new byte[buffer_size];
    }

    @Override
    public void close() throws IOException {
        //todo your code here
        fileNode = null;
    }
}
