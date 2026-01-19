package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    void addProduct(Product product);
    void deleteProduct(int id);
}