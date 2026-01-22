package com.upb.agripos.controller;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.PosView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Controller untuk POS Application
 * Menerapkan pola MVC dari Bab 12-13
 * Menerapkan DIP: View → Controller → Service → DAO
 */
public class PosController {
    private final ProductService productService;
    private final CartService cartService;
    private final PosView view;

    public PosController(ProductService productService, CartService cartService, PosView view) {
        this.productService = productService;
        this.cartService = cartService;
        this.view = view;
        initController();
        loadProductData();
        updateCartDisplay();
    }

    /**
     * Menginisialisasi event handler untuk semua button
     */
    private void initController() {
        // Event Handler: Tambah Produk
        view.getBtnAddProduct().setOnAction(e -> handleAddProduct());

        // Event Handler: Hapus Produk
        view.getBtnDeleteProduct().setOnAction(e -> handleDeleteProduct());

        // Event Handler: Tambah ke Keranjang
        view.getBtnAddToCart().setOnAction(e -> handleAddToCart());

        // Event Handler: Hapus dari Keranjang
        view.getBtnRemoveFromCart().setOnAction(e -> handleRemoveFromCart());

        // Event Handler: Clear Keranjang
        view.getBtnClearCart().setOnAction(e -> handleClearCart());

        // Event Handler: Checkout
        view.getBtnCheckout().setOnAction(e -> handleCheckout());
    }

    /**
     * Memuat daftar produk dari database ke TableView
     */
    private void loadProductData() {
        try {
            ObservableList<Product> data = FXCollections.observableArrayList(
                productService.getAllProducts()
            );
            view.getProductTable().setItems(data);
        } catch (Exception e) {
            showAlert("Error", "Gagal memuat data produk: " + e.getMessage());
        }
    }

    /**
     * Handle: Tambah Produk
     */
    private void handleAddProduct() {
        try {
            Product p = view.getProductFromInput();
            productService.addProduct(p);
            loadProductData();
            view.clearProductInput();
            showAlert("Success", "Produk berhasil ditambahkan!");
        } catch (NumberFormatException ex) {
            showAlert("Input Error", "Harga dan Stok harus berupa angka");
        } catch (IllegalArgumentException ex) {
            showAlert("Validasi Error", ex.getMessage());
        } catch (Exception ex) {
            showAlert("Error", "Gagal menyimpan: " + ex.getMessage());
        }
    }

    /**
     * Handle: Hapus Produk
     */
    private void handleDeleteProduct() {
        Product selected = view.getProductTable().getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Pilih produk yang akan dihapus");
            return;
        }

        try {
            productService.deleteProduct(selected.getCode());
            loadProductData();
            showAlert("Success", "Produk berhasil dihapus!");
        } catch (Exception ex) {
            showAlert("Error", "Gagal menghapus: " + ex.getMessage());
        }
    }

    /**
     * Handle: Tambah ke Keranjang
     */
    private void handleAddToCart() {
        Product selected = view.getProductTable().getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Pilih produk yang akan ditambahkan ke keranjang");
            return;
        }

        try {
            int quantity = view.getQuantityFromInput();
            if (quantity <= 0) {
                showAlert("Validasi", "Jumlah harus lebih dari 0");
                return;
            }

            cartService.addItemToCart(selected.getCode(), quantity);
            updateCartDisplay();
            view.clearQuantityInput();
            showAlert("Success", "Item berhasil ditambahkan ke keranjang!");
        } catch (NumberFormatException ex) {
            showAlert("Input Error", "Jumlah harus berupa angka");
        } catch (IllegalArgumentException ex) {
            showAlert("Validasi Error", ex.getMessage());
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    /**
     * Handle: Hapus dari Keranjang
     */
    private void handleRemoveFromCart() {
        CartItem selected = view.getCartTable().getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Pilih item yang akan dihapus dari keranjang");
            return;
        }

        try {
            cartService.removeItemFromCart(selected.getProduct().getCode());
            updateCartDisplay();
            showAlert("Success", "Item berhasil dihapus dari keranjang!");
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    /**
     * Handle: Clear Keranjang
     */
    private void handleClearCart() {
        if (cartService.isCartEmpty()) {
            showAlert("Info", "Keranjang sudah kosong");
            return;
        }

        cartService.clearCart();
        updateCartDisplay();
        showAlert("Success", "Keranjang telah dikosongkan");
    }

    /**
     * Handle: Checkout
     * Mencetak struk dan mengosongkan keranjang
     */
    private void handleCheckout() {
        if (cartService.isCartEmpty()) {
            showAlert("Warning", "Keranjang kosong, tidak ada yang perlu di-checkout");
            return;
        }

        try {
            printReceipt();
            cartService.clearCart();
            updateCartDisplay();
            showAlert("Success", "Checkout berhasil! Terima kasih atas pembelian Anda.");
        } catch (Exception ex) {
            showAlert("Error", "Gagal melakukan checkout: " + ex.getMessage());
        }
    }

    /**
     * Mencetak struk ke console
     */
    private void printReceipt() {
        System.out.println("\n========== AGRI-POS RECEIPT ==========");
        System.out.println("Kasir: Haidar Habibi Al Farisi-240202862");
        System.out.println("-------------------------------------");
        
        for (CartItem item : cartService.getCartItems()) {
            System.out.printf("%s x%d = Rp. %.2f%n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getSubtotal()
            );
        }

        System.out.println("-------------------------------------");
        System.out.printf("TOTAL: Rp. %.2f%n", cartService.getCartTotal());
        System.out.println("=====================================\n");
    }

    /**
     * Mengupdate tampilan keranjang
     */
    private void updateCartDisplay() {
        try {
            ObservableList<CartItem> cartData = FXCollections.observableArrayList(
                cartService.getCartItems()
            );
            view.getCartTable().setItems(cartData);
            view.updateCartSummary(
                cartService.getCartItemCount(),
                cartService.getCartTotal()
            );
        } catch (Exception e) {
            showAlert("Error", "Gagal mengupdate tampilan keranjang: " + e.getMessage());
        }
    }

    /**
     * Menampilkan alert dialog
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}