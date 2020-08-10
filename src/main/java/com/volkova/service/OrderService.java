package com.volkova.service;

import com.volkova.model.Order;
import com.volkova.model.Product;
import com.volkova.model.User;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void update(Order order);

    void delete(Order order);

    Order findById(long id);

    List<Order> findOrdersByUser(User user);

    List<Order> findAll();

    double positionCalculate(Product product, int amount);

}
