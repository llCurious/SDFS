/*
 * Copyright (c) Jipzingking 2016.
 */

package main.java.sdfs;

import main.java.sdfs.datanode.DataNode;
import main.java.sdfs.namenode.FileNode;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class SDFSInputStream implements Closeable {
    private FileNode fileNode;
    private int offset = 0;

    public SDFSInputStream(FileNode fileNode){
        this.fileNode = fileNode;
    }
    public int read(byte[] b) throws IOException {
        //todo your code here

        //根据返回的FileNode，找到对应的block的位置
        int blockNum = fileNode.blockNum();
        List<LocatedBlock> blocks = fileNode.getBlockInfo(0);

        //调用DataNode，传入blockNumber读取信息
        LocatedBlock block = blocks.get(0);
        DataNode dn = new DataNode();
        return dn.read(block.blockNumber,offset,b.length,b);
    }

    @Override
    public void close() throws IOException {
        //todo your code here
        fileNode = null;
    }

    public void seek(int newPos) throws IndexOutOfBoundsException, IOException {
        //todo your code here
        this.offset = newPos;
    }
}
