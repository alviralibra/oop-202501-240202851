package com.upb.agripos.dao;
import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    void addProduct(Product p);
    void deleteProduct(int code);
}