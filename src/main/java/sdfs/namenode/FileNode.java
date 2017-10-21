/*
 * Copyright (c) Jipzingking 2016.
 */

package main.java.sdfs.namenode;

import main.java.sdfs.LocatedBlock;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class FileNode extends Entity implements Serializable {
    private static final long serialVersionUID = -5007570814999866661L;
    private final List<BlockInfo> blockInfos = new ArrayList<>();

    public FileNode(){

    }
    //todo your code here
    public int blockNum(){
        return blockInfos.size();
    }
    public List<BlockInfo> getBlockInfos(){
        return this.blockInfos;
    }
    public List<LocatedBlock> getBlockInfo(int index){
        return blockInfos.get(index).getLocatedBlocks();
    }
    public void add(InetAddress inetAddress, int blockNum) throws Exception{
        BlockInfo blockInfo = new BlockInfo();
        blockInfo.addBlock(new LocatedBlock(inetAddress,blockNum));
        blockInfos.add(blockInfo);
    }

}

class BlockInfo implements Serializable {
    private static final long serialVersionUID = 8712105981933359634L;

    private final List<LocatedBlock> locatedBlocks = new ArrayList<>();

    //todo your code here
    public List<LocatedBlock> getLocatedBlocks() {
        return locatedBlocks;
    }
    public void addBlock(LocatedBlock block){
        this.locatedBlocks.add(block);
    }
}