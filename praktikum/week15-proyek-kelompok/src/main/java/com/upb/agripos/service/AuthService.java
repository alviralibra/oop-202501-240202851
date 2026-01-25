package com.upb.agripos.service;

import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.model.User;
import com.upb.agripos.service.exception.ValidationException;

/**
 * Service untuk Authentication & Authorization
 * Mengimplementasikan FR-5 (Login & Hak Akses)
 * Single Responsibility: hanya mengelola autentikasi user
 */
public class AuthService {
    private UserDAO userDAO;
    private User currentUser;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.currentUser = null;
    }

    /**
     * Login user dengan username dan password
     */
    public User login(String username, String password) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username tidak boleh kosong");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password tidak boleh kosong");
        }
        
        User user = userDAO.findByUsername(username);
        
        if (user == null) {
            throw new ValidationException("User tidak ditemukan: " + username);
        }
        
        // Simple password check (in production, use bcrypt or similar)
        if (!user.getPassword().equals(password)) {
            throw new ValidationException("Password salah");
        }
        
        this.currentUser = user;
        System.out.println("✓ User logged in: " + username + " (" + user.getRole() + ")");
        return user;
    }

    /**
     * Logout user saat ini
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("✓ User logged out: " + currentUser.getUsername());
        }
        this.currentUser = null;
    }

    /**
     * Mendapatkan user yang sedang login
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Cek apakah user sudah login
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Cek apakah user memiliki role tertentu
     */
    public boolean hasRole(String role) {
        if (!isLoggedIn()) {
            return false;
        }
        return currentUser.getRole().equals(role);
    }

    /**
     * Cek apakah user adalah KASIR
     */
    public boolean isKasir() {
        return hasRole("KASIR");
    }

    /**
     * Cek apakah user adalah ADMIN
     */
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    /**
     * Validate user memiliki akses ke fitur tertentu
     */
    public void validateAccess(String requiredRole) throws ValidationException {
        if (!isLoggedIn()) {
            throw new ValidationException("User belum login");
        }
        
        if (!hasRole(requiredRole)) {
            throw new ValidationException("User tidak memiliki akses (membutuhkan: " + requiredRole + ")");
        }
    }
}
