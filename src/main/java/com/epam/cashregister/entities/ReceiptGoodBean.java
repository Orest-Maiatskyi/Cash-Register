package com.epam.cashregister.entities;

import java.io.Serializable;

public class ReceiptGoodBean implements Serializable {

    private String goodCode;
    private String goodTitle;
    private float goodPrice;
    private float goodQuantity;


    public String getGoodCode() {
        return goodCode;
    }

    public String getGoodTitle() {
        return goodTitle;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public float getGoodQuantity() {
        return goodQuantity;
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

    public void setGoodQuantity(float goodQuantity) {
        this.goodQuantity = goodQuantity;
    }
}
