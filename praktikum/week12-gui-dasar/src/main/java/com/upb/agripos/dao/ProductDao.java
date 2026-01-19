package com.upb.agripos.dao;

import java.util.List;

import com.upb.agripos.model.Product;

public interface ProductDao {
    void insert(Product product) throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
    Product findById(String code) throws Exception;
    List<Product> findAll() throws Exception;
}