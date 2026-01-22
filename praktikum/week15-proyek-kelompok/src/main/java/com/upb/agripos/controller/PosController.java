package com.upb.agripos.controller;

import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.dao.TransactionDAOImpl;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.CashPayment;
import com.upb.agripos.model.EWalletPayment;
import com.upb.agripos.model.PaymentMethod;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.PaymentService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.TransactionService;
import com.upb.agripos.service.exception.ValidationException;
import java.util.List;

/**
 * Controller untuk Main POS Application
 * Mengelola alur transaksi penjualan
 * Menghubungkan View dengan Service Layer (DIP)
 */
public class PosController {
    private ProductService productService;
    private CartService cartService;
    private PaymentService paymentService;
    private TransactionService transactionService;

    public PosController() throws Exception {
        this.productService = new ProductService(new ProductDAOImpl());
        this.cartService = new CartService();
        this.paymentService = new PaymentService();
        this.transactionService = new TransactionService(new TransactionDAOImpl(), new ProductDAOImpl());
    }

    // ==================== PRODUCT MANAGEMENT ====================

    /**
     * Mendapatkan semua produk
     */
    public List<Product> getAllProducts() throws Exception {
        return productService.getAllProducts();
    }

    /**
     * Menambah produk baru
     */
    public void addProduct(String code, String name, String category, double price, int stock) throws Exception {
        Product product = new Product(code, name, category, price, stock);
        productService.addProduct(product);
    }

    /**
     * Memperbarui produk
     */
    public void updateProduct(String code, String name, String category, double price, int stock) throws Exception {
        Product product = new Product(code, name, category, price, stock);
        productService.updateProduct(product);
    }

    /**
     * Menghapus produk
     */
    public void deleteProduct(String productCode) throws Exception {
        productService.deleteProduct(productCode);
    }

    /**
     * Mencari produk berdasarkan kode
     */
    public Product getProductByCode(String productCode) throws Exception {
        return productService.getProductByCode(productCode);
    }

    // ==================== CART MANAGEMENT ====================

    /**
     * Menambah item ke keranjang
     */
    public void addToCart(String productCode, int quantity) throws Exception {
        Product product = productService.getProductByCode(productCode);
        cartService.addItem(product, quantity);
    }

    /**
     * Menghapus item dari keranjang
     */
    public void removeFromCart(String productCode) throws Exception {
        cartService.removeItem(productCode);
    }

    /**
     * Mengubah quantity item di keranjang
     */
    public void updateCartItemQuantity(String productCode, int newQuantity) throws Exception {
        cartService.updateItemQuantity(productCode, newQuantity);
    }

    /**
     * Mendapatkan semua item di keranjang
     */
    public List<CartItem> getCartItems() {
        return cartService.getItems();
    }

    /**
     * Menghitung total belanja
     */
    public double getCartTotal() {
        return cartService.calculateTotal();
    }

    /**
     * Mendapatkan jumlah item di keranjang
     */
    public int getCartItemCount() {
        return cartService.getItemCount();
    }

    /**
     * Mengosongkan keranjang
     */
    public void clearCart() {
        cartService.clearCart();
    }

    /**
     * Mengecek apakah keranjang kosong
     */
    public boolean isCartEmpty() {
        return cartService.isEmpty();
    }

    // ==================== PAYMENT MANAGEMENT ====================

    /**
     * Set metode pembayaran ke TUNAI
     */
    public void setPaymentMethodCash() {
        paymentService.setPaymentMethod(new CashPayment());
    }

    /**
     * Set metode pembayaran ke E-WALLET
     */
    public void setPaymentMethodEWallet(String provider) {
        paymentService.setPaymentMethod(new EWalletPayment(provider, "", 1000000)); // mock balance
    }

    /**
     * Mendapatkan deskripsi metode pembayaran
     */
    public String getPaymentMethodDescription() {
        return paymentService.getPaymentDescription();
    }

    // ==================== TRANSACTION ====================

    /**
     * Melakukan checkout
     */
    public int checkout(int userId, String paymentMethod, double amount) throws Exception {
        // Validasi keranjang
        cartService.validateCart();
        
        // Validasi pembayaran
        if (!paymentService.processPayment(amount)) {
            throw new ValidationException("Pembayaran gagal");
        }
        
        // Proses transaksi
        int transactionId = transactionService.checkout(
            userId,
            cartService.getItems(),
            paymentMethod,
            cartService.calculateTotal()
        );
        
        // Clear keranjang setelah checkout sukses
        cartService.clearCart();
        
        return transactionId;
    }

    /**
     * Mendapatkan CartService untuk akses langsung
     */
    public CartService getCartService() {
        return cartService;
    }

    /**
     * Mendapatkan TransactionService untuk laporan
     */
    public TransactionService getTransactionService() {
        return transactionService;
    }

    /**
     * Mendapatkan PaymentService
     */
    public PaymentService getPaymentService() {
        return paymentService;
    }
}
