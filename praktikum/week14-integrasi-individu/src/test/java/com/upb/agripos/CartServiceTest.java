package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartServiceTest {
    @Test
    public void testTotalPrice() {
        CartService cartService = new CartService();
        Product p1 = new Product(1, "Product 1", 100.0, 10);
        Product p2 = new Product(2, "Product 2", 200.0, 5);
        
        cartService.addToCart(p1);
        cartService.addToCart(p2);
        
        assertEquals(300.0, cartService.getTotalPrice(), "Total harga harusnya 300.0");
    }
}