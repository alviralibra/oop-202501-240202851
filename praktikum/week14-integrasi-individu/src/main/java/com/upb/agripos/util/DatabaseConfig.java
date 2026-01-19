package com.upb.agripos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static Connection getConnection() throws SQLException {
        // Nama database: agripos_db, User: postgres, Password: 1010
        String url = "jdbc:postgresql://localhost:5432/agripos_db?currentSchema=public";
        String user = "postgres";
        String password = "1010"; 
        
        try {
            // Memastikan driver PostgreSQL terbaca
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver tidak ditemukan: " + e.getMessage());
        }
        
        return DriverManager.getConnection(url, user, password);
    }
}