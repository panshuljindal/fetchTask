package com.panshul.fetchactivity;

import java.util.List;

public class DisplayObject {
    String id;
    Integer listId;
    String name;

    public DisplayObject(String id, Integer listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
