package com.upb.agripos.model;

/**
 * Model untuk item dalam shopping cart
 * Merupakan bagian dari FR-2 (Transaksi Penjualan - Keranjang)
 * Menggunakan Collections (List di CartService)
 */
public class CartItem {
    private Product product;
    private int quantity;
    private double unitPrice;

    public CartItem() {
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    public CartItem(Product product, int quantity, double unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Business Logic Methods
    public double getSubtotal() {
        return unitPrice * quantity;
    }

    public boolean isValidQuantity() {
        return quantity > 0 && quantity <= product.getStock();
    }

    public void updateQuantity(int newQuantity) throws IllegalArgumentException {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }
        if (newQuantity > product.getStock()) {
            throw new IllegalArgumentException("Quantity melebihi stok tersedia");
        }
        this.quantity = newQuantity;
    }

    // Getters & Setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
