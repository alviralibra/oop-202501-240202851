package com.upb.agripos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class untuk Cart (Keranjang Belanja)
 * Mengikuti implementasi Bab 7 (Collections dan Keranjang)
 * Menggunakan ArrayList sebagai Collections untuk menyimpan items
 */
public class Cart {
    private final List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    /**
     * Menambahkan item ke keranjang
     * Jika produk sudah ada, tambah kuantitasnya
     * 
     * @param product Produk yang akan ditambahkan
     * @param quantity Jumlah produk
     * @throws IllegalArgumentException jika quantity invalid
     */
    public void addItem(Product product, int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }

        // Cek apakah produk sudah ada di keranjang
        for (CartItem item : items) {
            if (item.getProduct().getCode().equals(product.getCode())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        // Jika belum ada, tambahkan sebagai item baru
        items.add(new CartItem(product, quantity));
    }

    /**
     * Menghapus item dari keranjang berdasarkan product code
     */
    public void removeItem(String productCode) {
        items.removeIf(item -> item.getProduct().getCode().equals(productCode));
    }

    /**
     * Mendapatkan semua item dalam keranjang
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Menghitung total harga seluruh item dalam keranjang
     */
    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Menghitung total quantity item dalam keranjang
     */
    public int getTotalItems() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    /**
     * Mengosongkan keranjang
     */
    public void clear() {
        items.clear();
    }

    /**
     * Mengecek apakah keranjang kosong
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart{\n");
        for (CartItem item : items) {
            sb.append("  ").append(item).append("\n");
        }
        sb.append("  Total: ").append(getTotal()).append("\n}");
        return sb.toString();
    }
}