package com.upb.agripos.service;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service untuk mengelola Shopping Cart
 * Mengimplementasikan FR-2 (Transaksi Penjualan - Keranjang)
 * Menggunakan Collections (List<CartItem>) untuk manajemen items
 * Single Responsibility: hanya mengelola keranjang belanja
 */
public class CartService {
    private List<CartItem> cartItems;

    public CartService() {
        this.cartItems = new ArrayList<>();
    }

    /**
     * Menambah produk ke keranjang
     */
    public void addItem(Product product, int quantity) throws ValidationException {
        if (product == null) {
            throw new ValidationException("Produk tidak boleh null");
        }
        
        if (quantity <= 0) {
            throw new ValidationException("Quantity harus lebih dari 0");
        }
        
        if (quantity > product.getStock()) {
            throw new ValidationException("Quantity melebihi stok tersedia");
        }
        
        // Cek apakah produk sudah ada di keranjang
        Optional<CartItem> existing = cartItems.stream()
            .filter(item -> item.getProduct().getCode().equals(product.getCode()))
            .findFirst();
        
        if (existing.isPresent()) {
            // Update quantity jika produk sudah ada
            CartItem item = existing.get();
            int newQty = item.getQuantity() + quantity;
            
            if (newQty > product.getStock()) {
                throw new ValidationException("Total quantity melebihi stok tersedia");
            }
            
            item.setQuantity(newQty);
        } else {
            // Tambah item baru
            cartItems.add(new CartItem(product, quantity));
        }
    }

    /**
     * Menghapus item dari keranjang berdasarkan kode produk
     */
    public void removeItem(String productCode) throws ValidationException {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong");
        }
        
        boolean removed = cartItems.removeIf(item -> item.getProduct().getCode().equals(productCode));
        
        if (!removed) {
            throw new ValidationException("Item tidak ditemukan di keranjang");
        }
    }

    /**
     * Mengubah quantity item di keranjang
     */
    public void updateItemQuantity(String productCode, int newQuantity) throws ValidationException {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong");
        }
        
        if (newQuantity <= 0) {
            throw new ValidationException("Quantity harus lebih dari 0");
        }
        
        Optional<CartItem> item = cartItems.stream()
            .filter(ci -> ci.getProduct().getCode().equals(productCode))
            .findFirst();
        
        if (item.isEmpty()) {
            throw new ValidationException("Item tidak ditemukan di keranjang");
        }
        
        CartItem cartItem = item.get();
        if (newQuantity > cartItem.getProduct().getStock()) {
            throw new ValidationException("Quantity melebihi stok tersedia");
        }
        
        cartItem.setQuantity(newQuantity);
    }

    /**
     * Menghitung total belanja dari semua item di keranjang
     */
    public double calculateTotal() {
        return cartItems.stream()
            .mapToDouble(CartItem::getSubtotal)
            .sum();
    }

    /**
     * Menghitung jumlah item unik di keranjang
     */
    public int getItemCount() {
        return cartItems.size();
    }

    /**
     * Menghitung total quantity di keranjang
     */
    public int getTotalQuantity() {
        return cartItems.stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
    }

    /**
     * Mendapatkan semua items di keranjang
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(cartItems);
    }

    /**
     * Mendapatkan item spesifik dari keranjang
     */
    public CartItem getItem(String productCode) throws ValidationException {
        return cartItems.stream()
            .filter(item -> item.getProduct().getCode().equals(productCode))
            .findFirst()
            .orElseThrow(() -> new ValidationException("Item tidak ditemukan di keranjang"));
    }

    /**
     * Mengosongkan seluruh keranjang
     */
    public void clearCart() {
        cartItems.clear();
    }

    /**
     * Mengecek apakah keranjang kosong
     */
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * Validasi keranjang sebelum checkout
     */
    public void validateCart() throws ValidationException {
        if (isEmpty()) {
            throw new ValidationException("Keranjang kosong");
        }
        
        // Validasi setiap item
        for (CartItem item : cartItems) {
            if (!item.isValidQuantity()) {
                throw new ValidationException("Item " + item.getProduct().getCode() + " tidak valid (qty atau stok)");
            }
        }
    }
}
