package com.upb.agripos.service;

import com.upb.agripos.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private List<Product> cartItems = new ArrayList<>();

    // Ini yang diminta oleh PosView.java (Tampilan Aplikasi)
    public void addItem(Product product, int quantity) {
        // Anggap saja kita menambah produk sebanyak quantity
        for (int i = 0; i < quantity; i++) {
            cartItems.add(product);
        }
    }

    // Ini yang diminta oleh CartServiceTest.java (File Tes)
    public void addToCart(Product product) {
        addItem(product, 1);
    }

    // Ini yang diminta oleh PosView.java
    public double calculateTotal() {
        return cartItems.stream().mapToDouble(Product::getPrice).sum();
    }

    // Ini yang diminta oleh CartServiceTest.java
    public double getTotalPrice() {
        return calculateTotal();
    }
}