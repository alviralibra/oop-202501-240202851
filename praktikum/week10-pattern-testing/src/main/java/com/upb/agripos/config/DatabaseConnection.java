package com.upb.agripos.config;

public class DatabaseConnection {

    private static DatabaseConnection instance;

    // constructor private → wajib untuk Singleton
    private DatabaseConnection() {
        System.out.println("DatabaseConnection dibuat");
    }

    // global access point
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void connect() {
        System.out.println("Terhubung ke database...");
    }
}