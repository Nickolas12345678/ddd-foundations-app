package com.nickolas.ddd.valueobjects;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemDetailsTest {
    @Test
    void testValidOrderItemDetails() {
        Money price = new Money("USD", new BigDecimal("100.00"));

        OrderItemDetails orderItem = new OrderItemDetails("product123", 2, price);


        assertEquals("product123", orderItem.getProductId());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(price, orderItem.getPrice());
    }

    @Test
    void testInvalidQuantity() {
        Money price = new Money("USD", new BigDecimal("100.00"));

        assertThrows(IllegalArgumentException.class, () -> new OrderItemDetails("product123", 0, price));
        assertThrows(IllegalArgumentException.class, () -> new OrderItemDetails("product123", -1, price));
    }

    @Test
    void testCalculateTotalPrice() {
        Money price = new Money("USD", new BigDecimal("100.00"));

        OrderItemDetails orderItem = new OrderItemDetails("product123", 2, price);

        Money expectedTotalPrice = price.multiply(2);
        assertEquals(expectedTotalPrice, orderItem.calculateTotalPrice());
    }
}
