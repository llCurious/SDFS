package main.java.sdfs.persistent;

import main.java.sdfs.namenode.DirNode;
import main.java.sdfs.namenode.Entity;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SDFS_Save {
    private Entity entity;
    public SDFS_Save(Entity entity){
        this.entity = entity;
    }
    public int save(String name) throws IOException{
        XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("src/record/"+name+".xml")));
        xmlEncoder.writeObject(entity);
        xmlEncoder.close();
        return 1;
    }

}
