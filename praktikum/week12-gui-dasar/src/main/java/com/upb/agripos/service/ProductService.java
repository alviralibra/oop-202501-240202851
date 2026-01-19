package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDao;
import com.upb.agripos.model.Product;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProduct(String code, String name, double price, int stock) throws Exception {
        if (code.isEmpty() || name.isEmpty()) {
            throw new Exception("Kode dan Nama wajib diisi!");
        }
        Product p = new Product(code, name, price, stock);
        productDao.insert(p);
    }
}