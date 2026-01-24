package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

/**
 * Data Access Object Interface untuk Product
 * Mendefinisikan kontrak operasi CRUD untuk Product
 * Mengimplementasikan Dependency Inversion Principle
 */
public interface ProductDAO {
    /**
     * Menyimpan produk baru ke database
     */
    void save(Product product) throws Exception;

    /**
     * Memperbarui data produk yang sudah ada
     */
    void update(Product product) throws Exception;

    /**
     * Menghapus produk berdasarkan kode produk
     */
    void delete(String productCode) throws Exception;

    /**
     * Mengambil semua produk dari database
     */
    List<Product> findAll() throws Exception;

    /**
     * Mencari produk berdasarkan kode unik
     */
    Product findByCode(String productCode) throws Exception;

    /**
     * Mencari produk berdasarkan ID
     */
    Product findById(int productId) throws Exception;

    /**
     * Mencari produk berdasarkan pattern kode (untuk filter/search)
     * Contoh: cari "AP" akan mengembalikan semua produk dengan kode mengandung "AP"
     */
    List<Product> findByCodePattern(String codePattern) throws Exception;
}
