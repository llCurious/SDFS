/*
 * Copyright (c) Jipzingking 2016.
 */

package main.java.sdfs.namenode;

import main.java.sdfs.LocatedBlock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileNode extends Entity implements Serializable {
    private static final long serialVersionUID = -5007570814999866661L;
    private final List<BlockInfo> blockInfos = new ArrayList<>();


    //todo your code here
    public int blockNum(){
        blockInfos.add(new BlockInfo());
        return blockInfos.size();
    }
    public List<LocatedBlock> getBlockInfo(int index){
        return blockInfos.get(index).getLocatedBlocks();
    }

}

class BlockInfo implements Serializable {
    private static final long serialVersionUID = 8712105981933359634L;

    private final List<LocatedBlock> locatedBlocks = new ArrayList<>();

    //todo your code here
    public List<LocatedBlock> getLocatedBlocks() {
        return locatedBlocks;
    }
}