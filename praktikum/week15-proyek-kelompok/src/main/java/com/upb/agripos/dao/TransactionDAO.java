package com.upb.agripos.dao;

import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionItem;
import java.util.List;

/**
 * Data Access Object Interface untuk Transaction
 * Mengelola operasi CRUD untuk transaksi penjualan
 */
public interface TransactionDAO {
    /**
     * Menyimpan transaksi baru ke database
     * @return ID transaksi yang baru dibuat
     */
    int save(Transaction transaction) throws Exception;

    /**
     * Menyimpan item transaksi
     */
    void saveTransactionItem(TransactionItem item) throws Exception;

    /**
     * Mengambil semua transaksi
     */
    List<Transaction> findAll() throws Exception;

    /**
     * Mengambil transaksi berdasarkan ID
     */
    Transaction findById(int transactionId) throws Exception;

    /**
     * Mengambil transaksi berdasarkan user ID
     */
    List<Transaction> findByUserId(int userId) throws Exception;

    /**
     * Mengambil semua item dalam transaksi
     */
    List<TransactionItem> getTransactionItems(int transactionId) throws Exception;
}
