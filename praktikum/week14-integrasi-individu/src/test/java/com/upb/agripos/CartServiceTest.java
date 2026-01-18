package com.upb.agripos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upb.agripos.model.Product;

// Unit Test untuk CartService (Bab 10: Pattern & Testing)
// Menguji logic non-UI: perhitungan total, penambahan item, validasi
public class CartServiceTest {
    
    private CartService cartService;
    private ProductService mockProductService;

    // Mock ProductService (tanpa database, hanya memory)
    static class MockProductService extends ProductService {
        private final java.util.Map<String, Product> products = new java.util.HashMap<>();

        public MockProductService() {
            super(null); // DAO tidak digunakan
            // Initialize dengan beberapa produk dummy
            products.put("BNH-001", new Product("BNH-001", "Benih Padi", 25000, 100));
            products.put("PPK-001", new Product("PPK-001", "Pupuk Urea", 350000, 50));
            products.put("ALT-001", new Product("ALT-001", "Cangkul", 90000, 10));
        }

        @Override
        public Product getProductByCode(String code) throws Exception {
            return products.get(code);
        }
    }

    @BeforeEach
    public void setUp() {
        mockProductService = new MockProductService();
        cartService = new CartService(mockProductService);
    }

    // Test 1: Menambahkan item tunggal ke keranjang
    @Test
    public void testAddSingleItemToCart() throws Exception {
        cartService.addItemToCart("BNH-001", 5);
        
        assertEquals(1, cartService.getCartItems().size());
        assertEquals(5, cartService.getCartItemCount());
        assertEquals(125000, cartService.getCartTotal(), 0.01);
    }

    // Test 2: Menambahkan item yang sama (quantity harus bertambah)
     
    @Test
    public void testAddDuplicateItemToCart() throws Exception {
        cartService.addItemToCart("BNH-001", 5);
        cartService.addItemToCart("BNH-001", 3);
        
        assertEquals(1, cartService.getCartItems().size()); // Masih 1 item
        assertEquals(8, cartService.getCartItemCount()); // Total qty = 8
        assertEquals(200000, cartService.getCartTotal(), 0.01); // 8 * 25000
    }

    // Test 3: Menambahkan multiple items berbeda
    @Test
    public void testAddMultipleDifferentItems() throws Exception {
        cartService.addItemToCart("BNH-001", 2);
        cartService.addItemToCart("PPK-001", 1);
        cartService.addItemToCart("ALT-001", 1);
        
        assertEquals(3, cartService.getCartItems().size());
        assertEquals(4, cartService.getCartItemCount());
        
        // Total: (2*25000) + (1*350000) + (1*90000) = 490000
        assertEquals(490000, cartService.getCartTotal(), 0.01);
    }

    // Test 4: Validasi stok tidak cukup
    @Test
    public void testAddItemWithInsufficientStock() throws Exception {
        // Produk ALT-001 hanya punya stok 10, coba tambah 15
        assertThrows(IllegalArgumentException.class, () -> {
            cartService.addItemToCart("ALT-001", 15);
        });
    }

    // Test 5: Validasi quantity invalid (0 atau negatif)
    @Test
    public void testAddItemWithZeroQuantity() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            cartService.addItemToCart("BNH-001", 0);
        });
    }

    // Test 6: Produk tidak ditemukan
    @Test
    public void testAddNonExistentProduct() throws Exception {
        assertThrows(Exception.class, () -> {
            cartService.addItemToCart("INVALID-001", 5);
        });
    }

    // Test 7: Menghapus item dari keranjang
    @Test
    public void testRemoveItemFromCart() throws Exception {
        cartService.addItemToCart("BNH-001", 5);
        cartService.addItemToCart("PPK-001", 2);
        
        assertEquals(2, cartService.getCartItems().size());
        
        cartService.removeItemFromCart("BNH-001");
        
        assertEquals(1, cartService.getCartItems().size());
        assertEquals(2, cartService.getCartItemCount());
        assertEquals(700000, cartService.getCartTotal(), 0.01);
    }

    // Test 8: Clear keranjang
    @Test
    public void testClearCart() throws Exception {
        cartService.addItemToCart("BNH-001", 5);
        cartService.addItemToCart("PPK-001", 2);
        
        assertFalse(cartService.isCartEmpty());
        
        cartService.clearCart();
        
        assertTrue(cartService.isCartEmpty());
        assertEquals(0, cartService.getCartItemCount());
        assertEquals(0, cartService.getCartTotal(), 0.01);
    }

    // Test 9: Keranjang kosong pada awal
    @Test
    public void testEmptyCartOnInitialization() {
        assertTrue(cartService.isCartEmpty());
        assertEquals(0, cartService.getCartItemCount());
        assertEquals(0, cartService.getCartTotal(), 0.01);
    }

    // Test 10: Perhitungan total akurat
    @Test
    public void testAccurateTotalCalculation() throws Exception {
        cartService.addItemToCart("BNH-001", 10); // 10 * 25000 = 250000
        cartService.addItemToCart("PPK-001", 3);  // 3 * 350000 = 1050000
        
        double expectedTotal = 250000 + 1050000;
        assertEquals(expectedTotal, cartService.getCartTotal(), 0.01);
    }
}