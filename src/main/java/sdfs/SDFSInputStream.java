/*
 * Copyright (c) Jipzingking 2016.
 */

package main.java.sdfs;

import com.sun.xml.internal.bind.v2.model.core.ID;
import main.java.sdfs.datanode.DataNode;
import main.java.sdfs.datanode.IDataNode;
import main.java.sdfs.namenode.FileNode;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class SDFSInputStream implements Closeable {
    private FileNode fileNode;
    private int index = 0;
    private int offset = 0;
    private int block_size = IDataNode.blockSize;
    public SDFSInputStream(FileNode fileNode){
        this.fileNode = fileNode;
    }
    public int read(byte[] b) throws IOException {
        //todo your code here
        int length = b.length;
        int read_size = 0;
        List<LocatedBlock> blocks;
        for (int i = index; i < fileNode.blockNum(); i++) {
            System.out.println("index: "+i);
            if (read_size<b.length){
                //选取对应的文件block
                blocks = fileNode.getBlockInfo(i);

                //调用DataNode，传入blockNumber读取信息
                //todo 选取对应的block副本
                LocatedBlock block = blocks.get(0);
                DataNode dn = new DataNode();

                int tmp = Math.min(block_size-offset, length);
                byte[] arr = new byte[tmp];
                int read = dn.read(block.blockNumber,offset,tmp,arr);
                System.arraycopy(arr,0,b,read_size,tmp);
                read_size += read;
                length -= read;
                offset = (offset+read);
                if (offset == block_size){
                    offset = 0;
                    index++;
                }

            }else{
                return read_size;
            }

        }
        return 0;
    }

    @Override
    public void close() throws IOException {
        //todo your code here
        fileNode = null;
    }

    public void seek(int newPos) throws IndexOutOfBoundsException, IOException {
        //todo your code here
        this.index = newPos/block_size;
        this.offset = newPos%block_size;
    }
}
