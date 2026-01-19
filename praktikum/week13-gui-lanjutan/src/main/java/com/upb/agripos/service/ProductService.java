package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDao;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;
import java.util.List;

public class ProductService {
    private ProductDao productDao = new ProductDAOImpl();

    public void addProduct(Product product) { productDao.addProduct(product); }
    public List<Product> getAllProducts() { return productDao.getAllProducts(); }
    public void deleteProduct(int id) { productDao.deleteProduct(id); }
}