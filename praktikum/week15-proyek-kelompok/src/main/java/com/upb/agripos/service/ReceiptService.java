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
        receipt.append("════════════════════════════════════════════════════\n");
        receipt.append("              AGRI-POS RECEIPT SYSTEM                \n");
        receipt.append("════════════════════════════════════════════════════\n");
        receipt.append("\n");
        
        // Header
        receipt.append(String.format("Transaction ID    : %-30s\n", transaction.getId()));
        receipt.append(String.format("Cashier           : %-30s\n", cashierName));
        receipt.append(String.format("Date & Time       : %-30s\n", 
            transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        receipt.append(String.format("Payment Method    : %-30s\n", transaction.getPaymentMethod()));
        
        receipt.append("\n");
        receipt.append("────────────────────────────────────────────────────\n");
        receipt.append("Item Details:\n");
        receipt.append("────────────────────────────────────────────────────\n");
        
        // Items with proper column headers
        int totalItems = 0;
        int totalQuantity = 0;
        
        if (transaction.getItems() != null && !transaction.getItems().isEmpty()) {
            for (TransactionItem item : transaction.getItems()) {
                receipt.append(String.format("%-25s %5d x %10.2f = %12.2f\n",
                    "Item " + item.getProductId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal()
                ));
                totalItems++;
                totalQuantity += item.getQuantity();
            }
        }
        
        receipt.append("────────────────────────────────────────────────────\n");
        receipt.append("\n");
        
        // Summary
        receipt.append(String.format("Total Items       : %-30d\n", totalItems));
        receipt.append(String.format("Total Qty         : %-30d\n", totalQuantity));
        receipt.append("\n");
        
        // Total
        receipt.append(String.format("%-30s Rp %15.2f\n", "TOTAL:", transaction.getTotalAmount()));
        
        receipt.append("\n");
        receipt.append("════════════════════════════════════════════════════\n");
        receipt.append("          Thank you for your purchase!              \n");
        receipt.append("════════════════════════════════════════════════════\n\n");
        
        return receipt.toString();
    }

    /**
     * Generate preview receipt dari cart items (sebelum checkout)
     */
    public static String generatePreviewReceipt(List<CartItem> cartItems, double totalAmount) {
        StringBuilder receipt = new StringBuilder();
        
        receipt.append("\n");
        receipt.append("════════════════════════════════════════════════════\n");
        receipt.append("         AGRI-POS PREVIEW RECEIPT                    \n");
        receipt.append("════════════════════════════════════════════════════\n");
        receipt.append("\n");
        
        receipt.append("Item Details:\n");
        receipt.append("────────────────────────────────────────────────────\n");
        
        int totalItems = 0;
        int totalQty = 0;
        
        for (CartItem item : cartItems) {
            receipt.append(String.format("%-25s %5d x %10.2f = %12.2f\n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
            ));
            totalItems++;
            totalQty += item.getQuantity();
        }
        
        receipt.append("────────────────────────────────────────────────────\n");
        receipt.append("\n");
        
        // Summary
        receipt.append(String.format("Total Items       : %-30d\n", totalItems));
        receipt.append(String.format("Total Qty         : %-30d\n", totalQty));
        receipt.append("\n");
        
        receipt.append(String.format("%-30s Rp %15.2f\n", "TOTAL:", totalAmount));
        receipt.append("════════════════════════════════════════════════════\n\n");
        
        return receipt.toString();
    }
}
