package com.panshul.fetchactivity;

public class ListIdObject {
    Integer id;
    Integer listId;
    String name;

    public ListIdObject(Integer id, Integer listId, String name){
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
