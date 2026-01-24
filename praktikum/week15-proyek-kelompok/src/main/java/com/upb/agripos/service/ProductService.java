package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.exception.ValidationException;
import java.util.List;

/**
 * Service untuk mengelola Produk
 * Mengimplementasikan Business Logic untuk FR-1 (Manajemen Produk)
 * Bergantung pada ProductDAO (DIP)
 * Single Responsibility: hanya mengelola CRUD dan validasi produk
 */
public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Menambah produk baru dengan validasi
     */
    public void addProduct(Product product) throws Exception {
        validateProduct(product);
        
        // Cek apakah kode produk sudah ada
        Product existing = productDAO.findByCode(product.getCode());
        if (existing != null) {
            throw new ValidationException("Kode produk sudah ada: " + product.getCode());
        }
        
        productDAO.save(product);
    }

    /**
     * Memperbarui produk dengan validasi
     */
    public void updateProduct(Product product) throws Exception {
        validateProduct(product);
        
        // Cek apakah produk ada
        Product existing = productDAO.findByCode(product.getCode());
        if (existing == null) {
            throw new ValidationException("Produk tidak ditemukan: " + product.getCode());
        }
        
        productDAO.update(product);
    }

    /**
     * Menghapus produk
     */
    public void deleteProduct(String productCode) throws Exception {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong");
        }
        
        Product existing = productDAO.findByCode(productCode);
        if (existing == null) {
            throw new ValidationException("Produk tidak ditemukan: " + productCode);
        }
        
        productDAO.delete(productCode);
    }

    /**
     * Mengambil semua produk
     */
    public List<Product> getAllProducts() throws Exception {
        return productDAO.findAll();
    }

    /**
     * Mencari produk berdasarkan kode
     */
    public Product getProductByCode(String productCode) throws Exception {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong");
        }
        
        Product product = productDAO.findByCode(productCode);
        if (product == null) {
            throw new ValidationException("Produk tidak ditemukan: " + productCode);
        }
        
        return product;
    }

    /**
     * Mencari produk berdasarkan ID
     */
    public Product getProductById(int productId) throws Exception {
        Product product = productDAO.findById(productId);
        if (product == null) {
            throw new ValidationException("Produk dengan ID " + productId + " tidak ditemukan");
        }
        
        return product;
    }

    /**
     * Mencari produk berdasarkan kode dengan pattern matching (untuk filter)
     * Digunakan oleh KASIR untuk pencarian cepat produk
     */
    public List<Product> searchByCode(String codePattern) throws Exception {
        if (codePattern == null || codePattern.trim().isEmpty()) {
            throw new ValidationException("Kode pencarian tidak boleh kosong");
        }
        
        return productDAO.findByCodePattern(codePattern.trim());
    }

    /**
     * Validasi produk sebelum save/update
     */
    private void validateProduct(Product product) throws ValidationException {
        if (product == null) {
            throw new ValidationException("Produk tidak boleh null");
        }
        
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong");
        }
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidationException("Nama produk tidak boleh kosong");
        }
        
        if (product.getPrice() < 0) {
            throw new ValidationException("Harga produk tidak boleh negatif");
        }
        
        if (product.getStock() < 0) {
            throw new ValidationException("Stok produk tidak boleh negatif");
        }
    }
}
