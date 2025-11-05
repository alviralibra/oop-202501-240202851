package com.upb.agripos.model.pembayaran;

import com.upb.agripos.model.kontrak.Receiptable;
import com.upb.agripos.model.kontrak.Validatable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {
    private String akunBank;
    private String otp;
    private static final double BIAYA_TRANSFER = 2500;

    public TransferBank(String invoiceNo, double total, String akunBank, String otp) {
        super(invoiceNo, total);
        this.akunBank = akunBank;
        this.otp = otp;
    }

    @Override
    public double biaya() {
        return BIAYA_TRANSFER;
    }

    @Override
    public boolean validasi() {
        return otp != null && otp.length() == 6;
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi();
    }

    @Override
    public String cetakStruk() {
        return String.format(
            "INVOICE %s | TOTAL+BIAYA: %.0f (%.0f + %.0f) | TRANSFER BANK: %s | STATUS: %s",
            invoiceNo, totalBayar(), total, biaya(), akunBank, prosesPembayaran() ? "BERHASIL" : "GAGAL"
        );
    }
}
