package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

/**
 * Service layer untuk Product
 * Mengikuti pattern MVC dari Bab 12-13
 * Menerapkan DIP (Dependency Inversion Principle) dari Bab 6
 * Service bergantung pada abstraksi (ProductDAO interface), bukan implementasi konkret
 */
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Menambah produk baru
     * Validasi dilakukan sebelum memanggil DAO
     */
    public void addProduct(Product product) throws Exception {
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Harga harus positif");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stok harus positif");
        }

        // Cek apakah produk dengan kode ini sudah ada
        Product existing = null;
        try {
            existing = productDAO.findByCode(product.getCode());
        } catch (Exception e) {
            // abaikan error jika tidak ditemukan
        }
        if (existing == null) {
            productDAO.insert(product);
        } else {
            // Jika sudah ada, update saja datanya
            productDAO.update(product);
        }
    }

    /**
     * Mengambil semua produk dari database
     */
    public List<Product> getAllProducts() throws Exception {
        return productDAO.findAll();
    }

    /**
     * Mencari produk berdasarkan kode
     */
    public Product getProductByCode(String code) throws Exception {
        return productDAO.findByCode(code);
    }

    /**
     * Menghapus produk berdasarkan kode
     */
    public void deleteProduct(String code) throws Exception {
        productDAO.delete(code);
    }

    /**
     * Mengubah data produk
     */
    public void updateProduct(Product product) throws Exception {
        productDAO.update(product);
    }
}