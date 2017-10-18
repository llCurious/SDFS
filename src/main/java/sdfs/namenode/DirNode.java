/*
 * Copyright (c) Jipzingking 2016.
 */

package main.java.sdfs.namenode;

import java.util.ArrayList;
import java.util.List;

public class DirNode extends Entity {


    //todo your code here
    private List<Entity> contents;

    public DirNode(int id){
        this.id = id;
        contents = new ArrayList<>();
//        System.out.println(this.name+"我的ID是："+this.id);
    }

    public List<Entity> getContents() {
        return contents;
    }
    public void addEntity(Entity entity){
        contents.add(entity);
    }

    public void setContents(List<Entity> contents) {
        this.contents = contents;
    }
}
