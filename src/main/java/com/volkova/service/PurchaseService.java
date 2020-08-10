package com.volkova.service;

import com.volkova.model.Product;
import com.volkova.model.Purchase;

import java.util.Map;

public interface PurchaseService {
    void createPurchase(Map<Product, Integer> positionProduct);

    public Purchase showPurchase(Purchase purchase);
}