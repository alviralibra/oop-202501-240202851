package com.upb.agripos.model;

import java.time.LocalDateTime;

/**
 * Model untuk Produk yang dijual di sistem POS
 * Merupakan entity utama untuk FR-1 (Manajemen Produk)
 */
public class Product {
    private int id;
    private String code;
    private String name;
    private String category;
    private double price;
    private int stock;
    private LocalDateTime createdAt;

    public Product() {
    }

    public Product(String code, String name, String category, double price, int stock) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public Product(int id, String code, String name, String category, double price, int stock) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    // Business Logic Methods
    public boolean isStockAvailable(int quantity) {
        return stock >= quantity;
    }

    public void reduceStock(int quantity) throws IllegalArgumentException {
        if (!isStockAvailable(quantity)) {
            throw new IllegalArgumentException("Stok tidak cukup. Stok tersedia: " + stock);
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    public double calculateSubtotal(int quantity) {
        return price * quantity;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
