package com.volkova.service;

import com.volkova.dao.OrderDao;
import com.volkova.dao.OrderDaoIml;
import com.volkova.model.Order;
import com.volkova.model.Product;
import com.volkova.model.User;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final OrderService orderService = new OrderServiceImpl();

    private final OrderDao orderDao = OrderDaoIml.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return orderService;
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderDao.findOrdersByUser(user);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public double positionCalculate(Product product, int amount) {
        return product.getPrice() * amount;
    }
}