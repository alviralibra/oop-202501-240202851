package com.upb.agripos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model untuk Transaksi Penjualan
 * Mewakili satu transaksi lengkap dengan items dan pembayaran
 */
public class Transaction {
    private int id;
    private int userId;
    private LocalDateTime transactionDate;
    private double totalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private List<TransactionItem> items;
    private LocalDateTime createdAt;

    public Transaction() {
        this.items = new ArrayList<>();
    }

    public Transaction(int userId, double totalAmount, String paymentMethod) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "COMPLETED";
        this.transactionDate = LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    // Business Logic Methods
    public void addItem(TransactionItem item) {
        this.items.add(item);
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(TransactionItem::getSubtotal)
                .sum();
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<TransactionItem> getItems() {
        return items;
    }

    public void setItems(List<TransactionItem> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", itemCount=" + items.size() +
                '}';
    }
}
