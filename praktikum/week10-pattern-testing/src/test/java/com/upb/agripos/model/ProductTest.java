package com.upb.agripos.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void testProductName() {
        Product product = new Product("P01", "Benih Jagung");
        assertEquals("Benih Jagung", product.getName());
    }

    @Test
    public void testProductCode() {
        Product product = new Product("P02", "Pupuk Cair");
        assertEquals("P02", product.getCode());
    }
}