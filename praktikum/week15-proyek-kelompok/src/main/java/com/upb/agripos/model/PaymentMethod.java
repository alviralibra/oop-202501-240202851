package com.upb.agripos.model;

/**
 * Interface untuk berbagai metode pembayaran
 * Mengimplementasikan Strategy Pattern untuk FR-3 (Metode Pembayaran)
 * OCP (Open/Closed Principle): Terbuka untuk penambahan metode baru
 */
public interface PaymentMethod {
    /**
     * Proses pembayaran dengan metode tertentu
     * @param amount Jumlah pembayaran
     * @return hasil pembayaran (true jika sukses)
     * 
     */
    // proses pembayaran
    boolean process(double amount);

    /**
     * Mendapatkan deskripsi metode pembayaran
     */
    String getDescription();

    /**
     * Validasi pembayaran (optional)
     */
    boolean validate(double amount);
}
