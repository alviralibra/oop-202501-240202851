package com.upb.agripos.model;

/**
 * Model class untuk Product (Produk)
 * Mengikuti struktur dari Bab 2 (Class dan Object)
 */
public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
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

    /**
     * Mengurangi stok produk
     * Mengikuti implementasi dari Bab 9 (Exception Handling)
     */
    public void reduceStock(int qty) throws IllegalArgumentException {
        if (qty > this.stock) {
            throw new IllegalArgumentException(
                "Stok tidak cukup untuk produk: " + this.name + 
                " (diminta: " + qty + ", tersedia: " + this.stock + ")"
            );
        }
        this.stock -= qty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}