package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import java.util.List;

public class ProductController {
    private ProductService productService = new ProductService();

    public void addProduct(String name, double price, int stock) {
        // Kita kirim 0 untuk code karena database akan mengisi otomatis (SERIAL)
        productService.addProduct(new Product(0, name, price, stock));
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public void deleteProduct(int code) {
        productService.deleteProduct(code);
    }
}