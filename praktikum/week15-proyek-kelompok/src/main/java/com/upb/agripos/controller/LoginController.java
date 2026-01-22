package com.upb.agripos.controller;

import com.upb.agripos.dao.UserDAOImpl;
import com.upb.agripos.model.User;
import com.upb.agripos.service.AuthService;
import com.upb.agripos.service.exception.ValidationException;

/**
 * Controller untuk Login
 * Menangani autentikasi user
 * Memisahkan View dari Business Logic (DIP)
 */
public class LoginController {
    private AuthService authService;

    public LoginController() throws Exception {
        this.authService = new AuthService(new UserDAOImpl());
    }

    /**
     * Proses login user
     */
    public User authenticate(String username, String password) throws Exception {
        try {
            return authService.login(username, password);
        } catch (ValidationException e) {
            throw new ValidationException("Login gagal: " + e.getMessage());
        }
    }

    /**
     * Logout user
     */
    public void logout() {
        authService.logout();
    }

    /**
     * Mendapatkan user saat ini
     */
    public User getCurrentUser() {
        return authService.getCurrentUser();
    }

    /**
     * Cek apakah user sudah login
     */
    public boolean isLoggedIn() {
        return authService.isLoggedIn();
    }

    public AuthService getAuthService() {
        return authService;
    }
}
