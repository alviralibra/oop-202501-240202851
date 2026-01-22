package com.upb.agripos.service;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionItem;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service untuk generate Receipt/Struk
 * Mengimplementasikan FR-4 (Struk & Laporan)
 * Single Responsibility: hanya generate format struk
 */
public class ReceiptService {

    public static String generateReceipt(Transaction transaction, String cashierName) {
        StringBuilder receipt = new StringBuilder();
        
        receipt.append("\n");
        receipt.append("════════════════════════════════════\n");
        receipt.append("        AGRI-POS RECEIPT SYSTEM       \n");
        receipt.append("════════════════════════════════════\n");
        receipt.append("\n");
        
        // Header
        receipt.append("Transaction ID    : ").append(transaction.getId()).append("\n");
        receipt.append("Cashier           : ").append(cashierName).append("\n");
        receipt.append("Date & Time       : ").append(
            transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        ).append("\n");
        receipt.append("Payment Method    : ").append(transaction.getPaymentMethod()).append("\n");
        
        receipt.append("\n");
        receipt.append("────────────────────────────────────\n");
        receipt.append("Item Details:\n");
        receipt.append("────────────────────────────────────\n");
        
        // Items
        if (transaction.getItems() != null && !transaction.getItems().isEmpty()) {
            for (TransactionItem item : transaction.getItems()) {
                receipt.append(String.format("%-15s %5d x %8.2f = %10.2f\n",
                    "Item " + item.getProductId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal()
                ));
            }
        }
        
        receipt.append("────────────────────────────────────\n");
        
        // Total
        receipt.append(String.format("%-25s Rp %12.2f\n", "TOTAL:", transaction.getTotalAmount()));
        
        receipt.append("\n");
        receipt.append("════════════════════════════════════\n");
        receipt.append("     Thank you for your purchase!     \n");
        receipt.append("════════════════════════════════════\n\n");
        
        return receipt.toString();
    }

    /**
     * Generate preview receipt dari cart items (sebelum checkout)
     */
    public static String generatePreviewReceipt(List<CartItem> cartItems, double totalAmount) {
        StringBuilder receipt = new StringBuilder();
        
        receipt.append("\n");
        receipt.append("════════════════════════════════════\n");
        receipt.append("     AGRI-POS PREVIEW RECEIPT        \n");
        receipt.append("════════════════════════════════════\n");
        receipt.append("\n");
        
        receipt.append("Item Details:\n");
        receipt.append("────────────────────────────────────\n");
        
        for (CartItem item : cartItems) {
            receipt.append(String.format("%-20s %5d x %8.2f = %10.2f\n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
            ));
        }
        
        receipt.append("────────────────────────────────────\n");
        receipt.append(String.format("%-25s Rp %12.2f\n", "TOTAL:", totalAmount));
        receipt.append("════════════════════════════════════\n\n");
        
        return receipt.toString();
    }
}
