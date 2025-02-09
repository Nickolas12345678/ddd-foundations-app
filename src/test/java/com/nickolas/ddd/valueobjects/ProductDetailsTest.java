package com.nickolas.ddd.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductDetailsTest {
    @Test
    void testValidProductDetails() {
        Dimensions dimensions = new Dimensions(10, 20, 30);
        ProductDetails product = new ProductDetails("Laptop", "A high-performance laptop", dimensions);


        assertEquals("Laptop", product.getName());
        assertEquals("A high-performance laptop", product.getDescription());
        assertEquals(dimensions, product.getDimensions());
    }

    @Test
    void testEmptyProductName() {
        Dimensions dimensions = new Dimensions(10, 20, 30);


        assertThrows(IllegalArgumentException.class, () -> {
            new ProductDetails("", "A high-performance laptop", dimensions);
        });
    }

    @Test
    void testCombineDescriptions() {
        Dimensions dimensions = new Dimensions(10, 20, 30);
        ProductDetails product = new ProductDetails("Laptop", "A high-performance laptop", dimensions);


        String combinedDescription = product.combineDescriptions("with great battery life");
        assertEquals("A high-performance laptop with great battery life", combinedDescription);
    }
}
