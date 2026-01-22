package com.upb.agripos.model;

/**
 * Implementasi pembayaran E-WALLET
 * Mengimplementasikan Strategy Pattern
 * Dapat diperluas untuk berbagai jenis e-wallet (GCash, Dana, OVO, dll)
 */
public class EWalletPayment implements PaymentMethod {
    private String walletProvider; // GCash, Dana, OVO, etc
    private String walletNumber;
    private double availableBalance;

    public EWalletPayment() {
        this("GENERIC", "", 0);
    }

    public EWalletPayment(String walletProvider, String walletNumber, double availableBalance) {
        this.walletProvider = walletProvider;
        this.walletNumber = walletNumber;
        this.availableBalance = availableBalance;
    }

    @Override
    public boolean process(double amount) {
        // Proses charge ke e-wallet
        if (availableBalance >= amount) {
            this.availableBalance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Pembayaran E-Wallet (" + walletProvider + ")";
    }

    @Override
    public boolean validate(double amount) {
        // Validasi saldo cukup
        return availableBalance >= amount && amount > 0;
    }

    // Getters & Setters
    public String getWalletProvider() {
        return walletProvider;
    }

    public void setWalletProvider(String walletProvider) {
        this.walletProvider = walletProvider;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Override
    public String toString() {
        return "EWalletPayment{" +
                "walletProvider='" + walletProvider + '\'' +
                ", walletNumber='" + walletNumber + '\'' +
                ", availableBalance=" + availableBalance +
                '}';
    }
}
