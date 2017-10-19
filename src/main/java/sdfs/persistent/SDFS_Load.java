package main.java.sdfs.persistent;

import main.java.sdfs.namenode.DirNode;
import main.java.sdfs.namenode.Entity;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SDFS_Load  {
    private String xml;
    public SDFS_Load(String  path){
        this.xml = path;
    }
    public Entity load() throws IOException{
        XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(xml)));
        Entity root = (Entity) xmlDecoder.readObject();
        return root;
    }
}
