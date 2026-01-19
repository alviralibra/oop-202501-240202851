package com.upb.agripos.service;

import com.upb.agripos.dao.JdbcProductDAO; // Pastikan namanya JdbcProductDAO
import com.upb.agripos.model.Product;
import com.upb.agripos.InvalidPriceException;
import java.util.List;

public class ProductService {
    // Ubah ProductDAOImpl menjadi JdbcProductDAO
    private JdbcProductDAO productDAO = new JdbcProductDAO();

    public List<Product> listProducts() {
        return productDAO.getAllProducts();
    }

    public void addProduct(String name, double price, int stock) throws InvalidPriceException {
        if (price <= 0) throw new InvalidPriceException("Harga tidak boleh nol atau negatif!");
        Product p = new Product(0, name, price, stock);
        productDAO.addProduct(p);
    }
}