package com.epam.cashregister.entities;

import java.io.Serializable;

public class MeasurementBean implements Serializable {

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
