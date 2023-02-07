package com.epam.cashregister.entities;

import java.io.Serializable;

public class WarehouseBean implements Serializable {

    private int id;
    private GoodBean good;
    private StorageBean storageBean;
    private float quantity;


    public int getId() {
        return id;
    }

    public GoodBean getGoodBean() {
        return good;
    }

    public StorageBean getStorageBean() {
        return storageBean;
    }

    public float getQuantity() {
        return quantity;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setGood(GoodBean good) {
        this.good = good;
    }

    public void setStorageBean(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
