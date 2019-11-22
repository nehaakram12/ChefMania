package com.app.chefmania.chefmania.Model;

public class Table {
    String name;
    int capacity;
    int status;

    public Table(String name, int capacity, int status) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
