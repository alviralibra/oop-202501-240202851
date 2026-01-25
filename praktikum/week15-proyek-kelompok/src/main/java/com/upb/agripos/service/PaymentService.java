package com.upb.agripos.service;

import com.upb.agripos.model.PaymentMethod;

/**
 * Service untuk mengelola Pembayaran
 * Mengimplementasikan Strategy Pattern untuk FR-3 (Metode Pembayaran)
 * Memungkinkan penambahan metode pembayaran baru tanpa mengubah kode yang ada (OCP)
 * Single Responsibility: hanya mengelola proses pembayaran
 */
public class PaymentService {
    private PaymentMethod paymentMethod;

    public PaymentService() {
    }

    /**
     * Set metode pembayaran (Strategy Pattern)
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Proses pembayaran dengan metode yang telah dipilih
     */
    public boolean processPayment(double amount) throws IllegalStateException {
        if (paymentMethod == null) {
            throw new IllegalStateException("Metode pembayaran belum dipilih");
        }
        
        if (amount <= 0) {
            throw new IllegalArgumentException("Jumlah pembayaran harus lebih dari 0");
        }
        
        if (!paymentMethod.validate(amount)) {
            throw new IllegalArgumentException("Validasi pembayaran gagal");
        }
        
        return paymentMethod.process(amount);
    }

    /**
     * Mendapatkan deskripsi metode pembayaran
     */
    public String getPaymentDescription() {
        if (paymentMethod == null) {
            return "Belum ada metode pembayaran";
        }
        return paymentMethod.getDescription();
    }

    /**
     * Mendapatkan metode pembayaran yang aktif
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
