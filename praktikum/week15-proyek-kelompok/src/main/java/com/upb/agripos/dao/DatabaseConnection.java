package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton Pattern untuk Database Connection
 * Mengelola koneksi ke PostgreSQL database
 * Memastikan hanya ada satu instance koneksi di aplikasi
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Database configuration
    private static final String URL = "jdbc:postgresql://localhost:5432/agripos_database";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "JERUKAGUNG";
    private static final String DRIVER = "org.postgresql.Driver";

    // Private constructor untuk Singleton Pattern
    private DatabaseConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✓ Database connected successfully");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL driver not found", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }

    /**
     * Mendapatkan instance Singleton dari DatabaseConnection
     * Menggunakan synchronized untuk thread safety
     */
    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Mendapatkan koneksi database aktif
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Menutup koneksi database
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Melakukan re-connect ke database
     */
    public void reconnect() throws SQLException {
        closeConnection();
        instance = null;
        getInstance();
    }
}
