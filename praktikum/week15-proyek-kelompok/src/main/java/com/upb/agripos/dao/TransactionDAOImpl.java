package com.upb.agripos.dao;

import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementasi TransactionDAO menggunakan JDBC
 * Mengelola operasi CRUD untuk Transaksi dan TransactionItems
 */
public class TransactionDAOImpl implements TransactionDAO {
    private Connection connection;

    public TransactionDAOImpl() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public int save(Transaction transaction) throws Exception {
        String sql = "INSERT INTO transactions (user_id, total_amount, payment_method, payment_status) " +
                    "VALUES (?, ?, ?, ?) RETURNING id";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setDouble(2, transaction.getTotalAmount());
            stmt.setString(3, transaction.getPaymentMethod());
            stmt.setString(4, transaction.getPaymentStatus());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        
        throw new SQLException("Saving transaction failed, could not retrieve ID.");
    }

    @Override
    public void saveTransactionItem(TransactionItem item) throws Exception {
        String sql = "INSERT INTO transaction_items (transaction_id, product_id, quantity, unit_price, subtotal) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getTransactionId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.setDouble(5, item.getSubtotal());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Saving transaction item failed, no rows affected.");
            }
        }
    }

    @Override
    public List<Transaction> findAll() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT id, user_id, total_amount, payment_method, payment_status, transaction_date " +
                    "FROM transactions ORDER BY transaction_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setTotalAmount(rs.getDouble("total_amount"));
                transaction.setPaymentMethod(rs.getString("payment_method"));
                transaction.setPaymentStatus(rs.getString("payment_status"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                
                transactions.add(transaction);
            }
        }
        
        return transactions;
    }

    @Override
    public Transaction findById(int transactionId) throws Exception {
        String sql = "SELECT id, user_id, total_amount, payment_method, payment_status, transaction_date " +
                    "FROM transactions WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setUserId(rs.getInt("user_id"));
                    transaction.setTotalAmount(rs.getDouble("total_amount"));
                    transaction.setPaymentMethod(rs.getString("payment_method"));
                    transaction.setPaymentStatus(rs.getString("payment_status"));
                    transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                    return transaction;
                }
            }
        }
        
        return null;
    }

    @Override
    public List<Transaction> findByUserId(int userId) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT id, user_id, total_amount, payment_method, payment_status, transaction_date " +
                    "FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setUserId(rs.getInt("user_id"));
                    transaction.setTotalAmount(rs.getDouble("total_amount"));
                    transaction.setPaymentMethod(rs.getString("payment_method"));
                    transaction.setPaymentStatus(rs.getString("payment_status"));
                    transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                    
                    transactions.add(transaction);
                }
            }
        }
        
        return transactions;
    }

    @Override
    public List<TransactionItem> getTransactionItems(int transactionId) throws Exception {
        List<TransactionItem> items = new ArrayList<>();
        String sql = "SELECT id, transaction_id, product_id, quantity, unit_price, subtotal " +
                    "FROM transaction_items WHERE transaction_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TransactionItem item = new TransactionItem();
                    item.setId(rs.getInt("id"));
                    item.setTransactionId(rs.getInt("transaction_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getDouble("unit_price"));
                    item.setSubtotal(rs.getDouble("subtotal"));
                    
                    items.add(item);
                }
            }
        }
        
        return items;
    }
}
