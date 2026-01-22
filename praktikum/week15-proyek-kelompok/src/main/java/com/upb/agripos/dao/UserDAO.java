package com.upb.agripos.dao;

import com.upb.agripos.model.User;

/**
 * Data Access Object Interface untuk User
 * Mendefinisikan kontrak operasi autentikasi user
 */
public interface UserDAO {
    /**
     * Mencari user berdasarkan username
     */
    User findByUsername(String username) throws Exception;

    /**
     * Menyimpan user baru
     */
    void save(User user) throws Exception;

    /**
     * Mengambil user berdasarkan ID
     */
    User findById(int userId) throws Exception;
}
