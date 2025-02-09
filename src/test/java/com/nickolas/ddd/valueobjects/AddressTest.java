package com.nickolas.ddd.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressTest {
    @Test
    void testValidAddressUSA() {
        Address address = new Address("USA", "New York", "5th Avenue", "10001");
        assertEquals("5th Avenue, New York, USA, 10001", address.toDisplayFormat());
    }

    @Test
    void testValidAddressUK() {
        Address address = new Address("UK", "London", "Baker Street", "SW1A 1AA");
        assertEquals("Baker Street, London, UK, SW1A 1AA", address.toDisplayFormat());
    }

    @Test
    void testValidAddressCanada() {
        Address address = new Address("Canada", "Toronto", "Queen Street", "M5H 2N2");
        assertEquals("Queen Street, Toronto, Canada, M5H 2N2", address.toDisplayFormat());
    }

    @Test
    void testInvalidPostalCode() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Address("USA", "Los Angeles", "Sunset Blvd", "1000");
        });
        assertEquals("Invalid postal code format", exception.getMessage());
    }

    @Test
    void testNullFields() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Address(null, "City", "Street", "12345");
        });
        assertEquals("All fields must be non-null", exception.getMessage());
    }

    @Test
    void testInvalidCountryPostalCode() {
        Address address = new Address("UnknownCountry", "SomeCity", "SomeStreet", "12345");
        assertEquals("12345", address.getPostalCode());
    }

    @Test
    void testAddressWithNullPostalCode() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Address("Canada", "Montreal", "St. Catherine Street", null);
        });
        assertEquals("All fields must be non-null", exception.getMessage());
    }
}
