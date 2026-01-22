package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test untuk CartService
 * Menguji business logic shopping cart
 * Test Case mencakup: add, remove, update, calculate total
 */
@DisplayName("CartService Unit Tests")
public class CartServiceTest {
    
    private CartService cartService;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        cartService = new CartService();
        product1 = new Product(1, "PROD-001", "Benih Padi", "Benih", 25000, 100);
        product2 = new Product(2, "PROD-002", "Pupuk Urea", "Pupuk", 150000, 50);
    }

    @Test
    @DisplayName("TC-001: Add item to empty cart should succeed")
    public void testAddItemToEmptyCart() throws ValidationException {
        cartService.addItem(product1, 5);
        
        assertEquals(1, cartService.getItemCount());
        assertEquals(5, cartService.getTotalQuantity());
        assertEquals(125000, cartService.calculateTotal());
    }

    @Test
    @DisplayName("TC-002: Add same product should update quantity")
    public void testAddSameProductUpdatesQuantity() throws ValidationException {
        cartService.addItem(product1, 5);
        cartService.addItem(product1, 3);
        
        assertEquals(1, cartService.getItemCount());
        assertEquals(8, cartService.getTotalQuantity());
        assertEquals(200000, cartService.calculateTotal());
    }

    @Test
    @DisplayName("TC-003: Add multiple different products")
    public void testAddMultipleDifferentProducts() throws ValidationException {
        cartService.addItem(product1, 5);
        cartService.addItem(product2, 2);
        
        assertEquals(2, cartService.getItemCount());
        assertEquals(7, cartService.getTotalQuantity());
        assertEquals(425000, cartService.calculateTotal()); // (5*25000) + (2*150000)
    }

    @Test
    @DisplayName("TC-004: Remove item from cart should succeed")
    public void testRemoveItemFromCart() throws ValidationException {
        cartService.addItem(product1, 5);
        cartService.addItem(product2, 2);
        
        cartService.removeItem("PROD-001");
        
        assertEquals(1, cartService.getItemCount());
        assertEquals(2, cartService.getTotalQuantity());
        assertEquals(300000, cartService.calculateTotal());
    }

    @Test
    @DisplayName("TC-005: Update item quantity should succeed")
    public void testUpdateItemQuantity() throws ValidationException {
        cartService.addItem(product1, 5);
        cartService.updateItemQuantity("PROD-001", 10);
        
        assertEquals(10, cartService.getItem("PROD-001").getQuantity());
        assertEquals(250000, cartService.calculateTotal());
    }

    @Test
    @DisplayName("TC-006: Add quantity exceeding stock should fail")
    public void testAddQuantityExceedingStock() {
        assertThrows(ValidationException.class, () -> {
            cartService.addItem(product1, 150); // stock only 100
        });
    }

    @Test
    @DisplayName("TC-007: Add zero or negative quantity should fail")
    public void testAddInvalidQuantity() {
        assertThrows(ValidationException.class, () -> {
            cartService.addItem(product1, 0);
        });
        
        assertThrows(ValidationException.class, () -> {
            cartService.addItem(product1, -5);
        });
    }

    @Test
    @DisplayName("TC-008: Add null product should fail")
    public void testAddNullProduct() {
        assertThrows(ValidationException.class, () -> {
            cartService.addItem(null, 5);
        });
    }

    @Test
    @DisplayName("TC-009: Remove non-existent item should fail")
    public void testRemoveNonExistentItem() throws ValidationException {
        cartService.addItem(product1, 5);
        
        assertThrows(ValidationException.class, () -> {
            cartService.removeItem("PROD-999");
        });
    }

    @Test
    @DisplayName("TC-010: Clear cart should empty all items")
    public void testClearCart() throws ValidationException {
        cartService.addItem(product1, 5);
        cartService.addItem(product2, 2);
        
        cartService.clearCart();
        
        assertTrue(cartService.isEmpty());
        assertEquals(0, cartService.getItemCount());
        assertEquals(0, cartService.calculateTotal());
    }

    @Test
    @DisplayName("TC-011: Empty cart validation should fail")
    public void testValidateEmptyCart() {
        assertThrows(ValidationException.class, () -> {
            cartService.validateCart();
        });
    }

    @Test
    @DisplayName("TC-012: Validate non-empty cart should succeed")
    public void testValidateNonEmptyCart() throws ValidationException {
        cartService.addItem(product1, 5);
        
        assertDoesNotThrow(() -> {
            cartService.validateCart();
        });
    }
}
