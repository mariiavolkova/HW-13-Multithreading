package com.volkova.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(positionProduct, that.positionProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionProduct);
    }
}