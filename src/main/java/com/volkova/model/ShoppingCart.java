package com.volkova.model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    public volatile Map<Product, Integer> positionProduct = new HashMap<>();

    public Map<Product, Integer> getUserCart() {
        return positionProduct;
    }

    public synchronized void addProduct(Product product, int quantity) {
        positionProduct.put(product, quantity);
    }

    public synchronized void deleteProduct(Product product) {
        positionProduct.remove(product);
    }

    public synchronized void cleanUserCart() {
        this.positionProduct = new HashMap<>();
    }
}