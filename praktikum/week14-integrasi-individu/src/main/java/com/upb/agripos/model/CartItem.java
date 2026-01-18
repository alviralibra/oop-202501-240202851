package com.upb.agripos.model;

/**
 * Model class untuk Cart Item (Item dalam Keranjang)
 * Mengikuti implementasi Bab 7 (Collections dan Keranjang)
 * Setiap item berisi informasi produk dan jumlah yang dibeli
 */
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Menghitung subtotal untuk item ini (harga * kuantitas)
     */
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getCode() + " - " + product.getName() + 
               " x" + quantity + " = " + getSubtotal();
    }
}