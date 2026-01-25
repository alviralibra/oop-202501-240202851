package com.upb.agripos.service.exception;

/**
 * Custom exception untuk kondisi stok produk tidak mencukupi
 * Digunakan ketika checkout tetapi stok produk tidak cukup
 */
public class OutOfStockException extends Exception {
    private String productCode;
    private int requiredQty;
    private int availableQty;

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, String productCode, int requiredQty, int availableQty) {
        super(message);
        this.productCode = productCode;
        this.requiredQty = requiredQty;
        this.availableQty = availableQty;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getRequiredQty() {
        return requiredQty;
    }

    public int getAvailableQty() {
        return availableQty;
    }
}
