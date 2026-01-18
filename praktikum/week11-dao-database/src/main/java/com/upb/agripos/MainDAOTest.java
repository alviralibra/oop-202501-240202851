package com.upb.agripos;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainDAOTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Menghubungkan ke database...");
        
        // Pastikan password sesuai dengan yang kamu buat di pgAdmin
        Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/agripos",
            "postgres",
            "1010" 
        );
        System.out.println("Koneksi berhasil...");

        ProductDAO dao = new ProductDAOImpl(conn);

        // 1. INSERT (Menambahkan Data)
        System.out.println("INSERT PRODUCT");
        dao.insert(new Product("P02", "Pupuk Organik", 25000, 10));
        System.out.println("Produk berhasil ditambahkan");

        // 2. UPDATE (Mengubah Data)
        System.out.println("Updating Product...");
        dao.update(new Product("P01", "Pupuk Organik Premium", 30000, 8));
        System.out.println("Produk berhasil diperbarui...");

        // 3. FIND (Mencari Data)
        System.out.println("FIND PRODUCT BY CODE");
        Product p = dao.findByCode("P01");
        if (p != null) {
            System.out.println("Data produk:");
            System.out.println("Kode  : " + p.getCode());
            System.out.println("Nama  : " + p.getName());
            System.out.println("Harga : " + p.getPrice());
            System.out.println("Stok  : " + p.getStock());
        }

        conn.close();
        System.out.println("Koneksi database ditutup...");
        
        // Tampilan Identitas Kamu
        System.out.println("------------------------------------");
        System.out.println("credit by Alvira Libra - 240202851");
        System.out.println("------------------------------------");
    }
}