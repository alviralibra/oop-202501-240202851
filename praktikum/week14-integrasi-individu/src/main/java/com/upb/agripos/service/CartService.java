package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

public class CartService {
    private final Cart cart;
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.cart = new Cart();
        this.productService = productService;
    }

    public void addItemToCart(String productCode, int quantity) throws Exception {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }

        Product product = productService.getProductByCode(productCode);
        if (product == null) {
            throw new Exception("Produk dengan kode " + productCode + " tidak ditemukan");
        }

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException(
                "Stok tidak cukup untuk " + product.getName() + 
                " (diminta: " + quantity + ", tersedia: " + product.getStock() + ")"
            );
        }

        cart.addItem(product, quantity);
    }

    // Menghapus item dari keranjang
    public void removeItemFromCart(String productCode) {
        cart.removeItem(productCode);
    }

    /**
     * Mendapatkan isi keranjang
     */
    public java.util.List<CartItem> getCartItems() {
        return cart.getItems();
    }

    /**
     * Menghitung total harga keranjang
     */
    public double getCartTotal() {
        return cart.getTotal();
    }

    /**
     * Mengosongkan keranjang (setelah checkout)
     */
    public void clearCart() {
        cart.clear();
    }

    /**
     * Mengecek apakah keranjang kosong
     */
    public boolean isCartEmpty() {
        return cart.isEmpty();
    }

    /**
     * Menghitung total item quantity di keranjang
     */
    public int getCartItemCount() {
        return cart.getTotalItems();
    }

    /**
     * Mendapatkan referensi cart (untuk testing)
     */
    public Cart getCart() {
        return cart;
    }
}