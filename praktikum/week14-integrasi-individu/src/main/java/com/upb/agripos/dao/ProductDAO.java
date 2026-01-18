package com.upb.agripos.dao;

import java.util.List;

import com.upb.agripos.model.Product;

/**
 * Interface ProductDAO
 * Mengikuti pattern dari Bab 11 (DAO dan JDBC)
 * Menentukan kontrak untuk akses data Product
 */
public interface ProductDAO {
    void insert(Product p) throws Exception;
    void update(Product p) throws Exception;
    void delete(String code) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
}