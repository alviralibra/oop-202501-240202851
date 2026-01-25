package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.TransactionDAO;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionItem;
import com.upb.agripos.service.exception.OutOfStockException;
import com.upb.agripos.service.exception.ValidationException;
import java.util.List;

/**
 * Service untuk mengelola Transaksi Penjualan
 * Mengimplementasikan koordinasi antara CartService, ProductService, dan PaymentService
 * Single Responsibility: mengelola alur transaksi
 */
public class TransactionService {
    private TransactionDAO transactionDAO;
    private ProductDAO productDAO;

    public TransactionService(TransactionDAO transactionDAO, ProductDAO productDAO) {
        this.transactionDAO = transactionDAO;
        this.productDAO = productDAO;
    }

    /**
     * Melakukan checkout: proses seluruh transaksi dari keranjang
     * @param userId ID user yang melakukan transaksi
     * @param cartItems List item dari keranjang
     * @param paymentMethod Metode pembayaran
     * @param totalAmount Total jumlah pembayaran
     * @return Transaction ID yang telah dibuat
     */
    public int checkout(int userId, List<CartItem> cartItems, String paymentMethod, double totalAmount) 
            throws Exception {
        
        // Validasi input
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ValidationException("Keranjang kosong, tidak bisa checkout");
        }
        
        if (totalAmount <= 0) {
            throw new ValidationException("Total amount harus lebih dari 0");
        }
        
        // Validasi stok untuk semua item
        for (CartItem item : cartItems) {
            if (!item.getProduct().isStockAvailable(item.getQuantity())) {
                throw new OutOfStockException(
                    "Stok produk " + item.getProduct().getCode() + " tidak cukup",
                    item.getProduct().getCode(),
                    item.getQuantity(),
                    item.getProduct().getStock()
                );
            }
        }
        
        // Create transaction
        Transaction transaction = new Transaction(userId, totalAmount, paymentMethod);
        int transactionId = transactionDAO.save(transaction);
        
        try {
            // Save transaction items dan update stok
            for (CartItem cartItem : cartItems) {
                Product product = cartItem.getProduct();
                
                // Create transaction item
                TransactionItem transItem = new TransactionItem(
                    transactionId,
                    product.getId(),
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
                );
                transItem.setSubtotal(cartItem.getSubtotal());
                transactionDAO.saveTransactionItem(transItem);
                
                // Update stok produk
                product.reduceStock(cartItem.getQuantity());
                productDAO.update(product);
            }
            
            System.out.println("✓ Transaction completed: ID=" + transactionId);
            return transactionId;
            
        } catch (Exception e) {
            // Rollback: hapus transaksi yang baru dibuat jika ada error
            System.err.println("✗ Transaction failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Mendapatkan history transaksi user
     */
    public List<Transaction> getUserTransactionHistory(int userId) throws Exception {
        return transactionDAO.findByUserId(userId);
    }

    /**
     * Mendapatkan detail transaksi beserta itemnya
     */
    public Transaction getTransactionDetails(int transactionId) throws Exception {
        Transaction transaction = transactionDAO.findById(transactionId);
        
        if (transaction == null) {
            throw new ValidationException("Transaksi tidak ditemukan");
        }
        
        List<TransactionItem> items = transactionDAO.getTransactionItems(transactionId);
        transaction.setItems(items);
        
        return transaction;
    }

    /**
     * Mendapatkan semua transaksi
     */
    public List<Transaction> getAllTransactions() throws Exception {
        return transactionDAO.findAll();
    }

    /**
     * Generate laporan penjualan (summary)
     */
    public TransactionSummary generateSalesReport() throws Exception {
        List<Transaction> allTransactions = getAllTransactions();
        
        double totalRevenue = allTransactions.stream()
            .mapToDouble(Transaction::getTotalAmount)
            .sum();
        
        int totalTransactions = allTransactions.size();
        double averageTransaction = totalTransactions > 0 ? totalRevenue / totalTransactions : 0;
        
        return new TransactionSummary(totalRevenue, totalTransactions, averageTransaction);
    }

    /**
     * Helper class untuk laporan transaksi
     */
    public static class TransactionSummary {
        public double totalRevenue;
        public int totalTransactions;
        public double averageTransaction;

        public TransactionSummary(double totalRevenue, int totalTransactions, double averageTransaction) {
            this.totalRevenue = totalRevenue;
            this.totalTransactions = totalTransactions;
            this.averageTransaction = averageTransaction;
        }

        @Override
        public String toString() {
            return "TransactionSummary{" +
                    "totalRevenue=" + totalRevenue +
                    ", totalTransactions=" + totalTransactions +
                    ", averageTransaction=" + averageTransaction +
                    '}';
        }
    }

    /**
     * Get daily sales data untuk grafik omset penjualan
     * @return Map<String(date), Double(revenue)> 
     */
    public java.util.Map<String, Double> getDailySalesData() throws Exception {
        List<Transaction> allTransactions = transactionDAO.findAll();
        
        java.util.Map<String, Double> dailySales = new java.util.LinkedHashMap<>();
        
        // Group by date (dd-MMM format)
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MMM");
        
        for (Transaction transaction : allTransactions) {
            java.time.LocalDateTime dateTime = transaction.getTransactionDate();
            String dateKey = dateTime.format(formatter);
            
            dailySales.put(dateKey, dailySales.getOrDefault(dateKey, 0.0) + transaction.getTotalAmount());
        }
        
        return dailySales;
    }
}
