package com.upb.agripos;

public class MainExceptionDemo {
    public static void main(String[] args) {
        System.out.println("Hello, Alvira-240202851 (Week9)");

        ShoppingCart cart = new ShoppingCart();
        Product p1 = new Product("P01", "Pupuk Organik", 25000, 3);
        // contoh penambahan stok
        try {
            // menambahkan produk dengan stok negatif
            cart.addProduct(p1, -1);
        } catch (InvalidQuantityException e) {
            // menampilkan informasi quantity tidak valid
            System.out.println("Kesalahan: " + e.getMessage());
        }
        // contoh pengurangan stok
        try {
            cart.removeProduct(p1);
        } catch (ProductNotFoundException e) {
            // menampilkan informasi produk tidak ditemukan
            System.out.println("Kesalahan: " + e.getMessage());
        }
        // jika stok kurang dari 5 maka tidak bisa checkout
        try {
            cart.addProduct(p1, 5);
            cart.checkout();
        } catch (Exception e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }
        // insufficient stock
        try {
            cart.checkout();
        } catch (InsufficientStockException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }
    }
}