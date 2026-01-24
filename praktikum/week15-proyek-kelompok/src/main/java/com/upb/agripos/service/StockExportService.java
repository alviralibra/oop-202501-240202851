package com.upb.agripos.service;

import com.upb.agripos.model.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service untuk export data stok produk
 * Menghasilkan file CSV yang bisa dibuka dengan Excel
 * Single Responsibility: export data ke format CSV
 */
public class StockExportService {
    
    /**
     * Export daftar produk ke file CSV
     * @param products List produk yang akan diexport
     * @param filePath Path file CSV (contoh: "stock_export.csv")
     * @return True jika berhasil, False jika gagal
     */
    public boolean exportToCSV(List<Product> products, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Header
            writer.append("LAPORAN STOK PRODUK AGRI-POS\n");
            writer.append("Export Date: ").append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
            writer.append("\n");
            
            // Table header dengan delimiter koma
            writer.append("No,ID,Kode,Nama Produk,Kategori,Harga (Rp),Stok\n");
            
            // Data rows
            int no = 1;
            for (Product product : products) {
                writer.append(String.valueOf(no)).append(",");
                writer.append(String.valueOf(product.getId())).append(",");
                writer.append("\"").append(product.getCode()).append("\",");
                writer.append("\"").append(product.getName()).append("\",");
                writer.append("\"").append(product.getCategory() != null ? product.getCategory() : "-").append("\",");
                writer.append(String.valueOf(product.getPrice())).append(",");
                writer.append(String.valueOf(product.getStock())).append("\n");
                no++;
            }
            
            // Summary section
            writer.append("\n");
            writer.append("RINGKASAN,,,,,\n");
            writer.append("Total Produk: ").append(String.valueOf(products.size())).append(",,,,,\n");
            
            double totalValue = products.stream()
                .mapToDouble(p -> p.getPrice() * p.getStock())
                .sum();
            writer.append("Total Nilai Stok: Rp ").append(String.format("%.0f", totalValue)).append(",,,,,\n");
            
            int totalStock = products.stream()
                .mapToInt(Product::getStock)
                .sum();
            writer.append("Total Unit Stok: ").append(String.valueOf(totalStock)).append(",,,,,\n");
            
            return true;
        }
    }
    
    /**
     * Generate default filename dengan timestamp
     */
    public String generateDefaultFilename() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "stok_produk_" + timestamp + ".csv";
    }
}
