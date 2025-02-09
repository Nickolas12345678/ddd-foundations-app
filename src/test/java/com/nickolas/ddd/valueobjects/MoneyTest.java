package com.nickolas.ddd.valueobjects;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    void testAdd_sameCurrency() {
        Money money1 = new Money("USD", new BigDecimal("50.00"));
        Money money2 = new Money("USD", new BigDecimal("30.00"));
        Money result = money1.add(money2);
        assertEquals(new Money("USD", new BigDecimal("80.00")), result, "The amounts should be added correctly in the same currency");
    }

    @Test
    void testAdd_differentCurrency() {
        Money money1 = new Money("USD", new BigDecimal("50.00"));
        Money money2 = new Money("EUR", new BigDecimal("30.00"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            money1.add(money2);
        });
        assertEquals("Currencies must be the same to add", exception.getMessage(), "Adding different currencies should throw an exception");
    }

    @Test
    void testSubtract_sameCurrency() {
        Money money1 = new Money("USD", new BigDecimal("50.00"));
        Money money2 = new Money("USD", new BigDecimal("20.00"));
        Money result = money1.subtract(money2);
        assertEquals(new Money("USD", new BigDecimal("30.00")), result, "The amounts should be subtracted correctly in the same currency");
    }

    @Test
    void testSubtract_differentCurrency() {
        Money money1 = new Money("USD", new BigDecimal("50.00"));
        Money money2 = new Money("EUR", new BigDecimal("20.00"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            money1.subtract(money2);
        });
        assertEquals("Currencies must be the same to subtract", exception.getMessage(), "Subtracting different currencies should throw an exception");
    }

    @Test
    void testEquals_sameValue() {
        Money money1 = new Money("USD", new BigDecimal("50.00"));
        Money money2 = new Money("USD", new BigDecimal("50.00"));
        assertTrue(money1.equals(money2), "Money objects with the same value and currency should be equal");
    }

    @Test
    void testEquals_differentValue() {
        Money money1 = new Money("USD", new BigDecimal("50.00"));
        Money money2 = new Money("USD", new BigDecimal("30.00"));
        assertFalse(money1.equals(money2), "Money objects with different values should not be equal");
    }

    @Test
    void testEquals_differentCurrency() {
        Money money1 = new Money("USD", new BigDecimal("50.00"));
        Money money2 = new Money("EUR", new BigDecimal("50.00"));
        assertFalse(money1.equals(money2), "Money objects with different currencies should not be equal");
    }

    @Test
    void testToDisplayFormat() {
        Money money = new Money("USD", new BigDecimal("50.00"));
        String result = money.toDisplayFormat();
        assertEquals("USD 50.00", result, "The display format should be correct");
    }

    @Test
    void testMultiply() {
        Money money = new Money("USD", new BigDecimal("50.00"));
        Money result = money.multiply(3);
        assertEquals(new Money("USD", new BigDecimal("150.00")), result, "The amount should be correctly multiplied by quantity");
    }

    @Test
    void testConstructor_invalidCurrency() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Money("", new BigDecimal("50.00"));
        });
        assertEquals("Currency cannot be null or empty", exception.getMessage(), "Currency should not be empty or null");
    }

    @Test
    void testConstructor_invalidAmount() {
        Money money = new Money("USD", new BigDecimal("0.00"));
        assertEquals(new BigDecimal("0.00"), money.getAmount(), "Amount can be zero or positive, and no exception should be thrown");
    }
}
