package com.volkova.model;

import java.util.Map;

public class Purchase {

    private Map<Product, Integer> positionProduct;
    private String cardNumber;
    private float totalSum;

    public Purchase(Map<Product, Integer> positionProduct, String cardNumber, float totalSum) {
        this.positionProduct = positionProduct;
        this.cardNumber = cardNumber;
        this.totalSum = totalSum;
    }

    public Map<Product, Integer> getPositionProduct() {
        return positionProduct;
    }

    public void setPositionProduct(Map<Product, Integer> positionProduct) {
        this.positionProduct = positionProduct;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public float getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(float totalSum) {
        this.totalSum = totalSum;
    }
}
