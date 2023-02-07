package com.epam.cashregister.entities;

import java.io.Serializable;

public class OrderBean implements Serializable {

    private int orderId;
    private String status;
    private String goodCode;
    private String goodTitle;
    private float goodPrice;
    private float quantity;
    private String storageAddress;
    private String dateTime;


    public int getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public String getGoodTitle() {
        return goodTitle;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public void setGoodTitle(String goodTitle) {
        this.goodTitle = goodTitle;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
