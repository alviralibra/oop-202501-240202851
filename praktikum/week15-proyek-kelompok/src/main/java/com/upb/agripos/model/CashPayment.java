package com.upb.agripos.model;

/**
 * Implementasi pembayaran TUNAI
 * Mengimplementasikan Strategy Pattern
 */
public class CashPayment implements PaymentMethod {
    private double changeAmount;

    public CashPayment() {
    }

    @Override
    public boolean process(double amount) {
        // Pembayaran tunai selalu valid jika jumlah tidak negatif
        return amount > 0;
    }

    @Override
    public String getDescription() {
        return "Pembayaran Tunai";
    }

    @Override
    public boolean validate(double amount) {
        // Validasi bahwa nominal pembayaran positif
        return amount > 0;
    }

    public void calculateChange(double totalBill, double amountPaid) throws IllegalArgumentException {
        if (amountPaid < totalBill) {
            throw new IllegalArgumentException("Uang tidak cukup");
        }
        this.changeAmount = amountPaid - totalBill;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    @Override
    public String toString() {
        return "CashPayment{" +
                "changeAmount=" + changeAmount +
                '}';
    }
}
