package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.model.Product;

/**
 * Implementasi ProductDAO dengan JDBC
 * Mengikuti pattern dari Bab 11 (Data Access Object)
 */
public class ProductDAOImpl implements ProductDAO {
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product p) throws Exception {
        if (p.getCode() == null || p.getCode().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        if (p.getPrice() < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif");
        }
        if (p.getStock() < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }

        String sql = "INSERT INTO products (code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, p.getCode());
            stmt.setString(2, p.getName());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getStock());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Gagal menambah produk: " + e.getMessage());
        }
    }

    @Override
    public void update(Product p) throws Exception {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setDouble(2, p.getPrice());
            stmt.setInt(3, p.getStock());
            stmt.setString(4, p.getCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Gagal mengubah produk: " + e.getMessage());
        }
    }

    @Override
    public void delete(String code) throws Exception {
        String sql = "DELETE FROM products WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new Exception("Produk dengan kode " + code + " tidak ditemukan");
            }
        } catch (SQLException e) {
            throw new Exception("Gagal menghapus produk: " + e.getMessage());
        }
    }

    @Override
    public Product findByCode(String code) throws Exception {
        String sql = "SELECT * FROM products WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                    );
                }
            }
        } catch (SQLException e) {
            throw new Exception("Gagal mencari produk: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            throw new Exception("Gagal mengambil daftar produk: " + e.getMessage());
        }
        return products;
    }
}