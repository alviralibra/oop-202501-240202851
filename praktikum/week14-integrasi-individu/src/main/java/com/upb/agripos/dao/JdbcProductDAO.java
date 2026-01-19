package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import com.upb.agripos.util.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDAO implements ProductDao {

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        // Query sesuai tabel kamu di PostgreSQL
        String query = "SELECT code, name, price, stock FROM public.products ORDER BY code ASC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("code"), 
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database Error (Get All): " + e.getMessage());
        }
        return products;
    }

    @Override
    public void addProduct(Product product) {
        String query = "INSERT INTO public.products (name, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getStock());
            pstmt.executeUpdate();
            System.out.println("Produk berhasil ditambahkan!");
        } catch (SQLException e) {
            System.err.println("Database Error (Add): " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(int code) {
        String query = "DELETE FROM public.products WHERE code = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, code);
            pstmt.executeUpdate();
            System.out.println("Produk berhasil dihapus!");
        } catch (SQLException e) {
            System.err.println("Database Error (Delete): " + e.getMessage());
        }
    }
}