package com.upb.agripos.model;

public class Product {
    private int code; // Di database adalah serial/int
    private String name;
    private double price;
    private int stock;

    public Product(int code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter wajib ada untuk TableView JavaFX
    public int getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}