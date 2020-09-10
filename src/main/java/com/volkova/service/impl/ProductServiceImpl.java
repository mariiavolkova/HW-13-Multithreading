package com.volkova.service.impl;

import com.volkova.dao.ProductDao;
import com.volkova.dao.impl.ProductDaoImpl;
import com.volkova.model.Product;
import com.volkova.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final ProductService productService = new ProductServiceImpl();

    private final ProductDao productDao = ProductDaoImpl.getInstance();

    private ProductServiceImpl() {
    }

    public static ProductService getInstance() {
        return productService;
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public List<Product> findByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public Product findById(long id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }
}
