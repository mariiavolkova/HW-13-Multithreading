package com.volkova.service;

import com.volkova.model.Product;
import com.volkova.model.Purchase;

import java.util.Map;

public interface PurchaseService {
    void createPurchase(Map<Product, Integer> positionProduct);

    Purchase showPurchase(Purchase purchase);
}