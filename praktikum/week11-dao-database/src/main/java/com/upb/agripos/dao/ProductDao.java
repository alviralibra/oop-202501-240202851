package com.upb.agripos.dao;

import java.util.List;
import com.upb.agripos.model.Product;

/**
 * Interface ini berisi daftar 'janji' fungsi apa saja yang harus ada
 * untuk mengolah data produk (CRUD).
 */
public interface ProductDAO {
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    void delete(String code) throws Exception;
}