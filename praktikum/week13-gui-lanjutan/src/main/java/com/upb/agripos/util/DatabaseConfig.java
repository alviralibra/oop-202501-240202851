package com.upb.agripos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static Connection getConnection() throws SQLException {
        // Nama database: agripos_db, User: postgres, Password: 123
        String url = "jdbc:postgresql://localhost:5432/agripos_db?currentSchema=public";
        String user = "postgres";
        String password = "1010"; 
        return DriverManager.getConnection(url, user, password);
    }
}