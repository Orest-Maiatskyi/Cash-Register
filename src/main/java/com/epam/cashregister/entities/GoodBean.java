package com.epam.cashregister.entities;

import java.io.Serializable;

public class GoodBean implements Serializable {

    private int id;
    private String code;
    private String title;
    private String description;
    private MeasurementBean measurement;
    private float price;


    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public MeasurementBean getMeasurement() {
        return measurement;
    }

    public float getPrice() {
        return price;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMeasurement(MeasurementBean measurement) {
        this.measurement = measurement;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return
                "id" + getId() + "\n" +
                "code: " + getCode() + "\n" +
                "title: " + getTitle() + "\n" +
                "description: " + getDescription() + "\n" +
                "measurement: " + getMeasurement() + "\n" +
                "price: " + getPrice() + "\n";
    }

}
