package com.nickolas.ddd.entities;

import com.nickolas.ddd.valueobjects.Address;
import com.nickolas.ddd.valueobjects.Email;
import com.nickolas.ddd.valueobjects.Name;
import com.nickolas.ddd.valueobjects.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private Customer customer;
    private Name name;
    private Email email;
    private Address address;

    @BeforeEach
    public void setUp() {
        name = new Name("John", "Doe");
        email = new Email("john.doe@example.com");
        address = new Address("USA", "New York", "123 Main St", "10001");

        customer = new Customer(UUID.randomUUID(), name, email, address);
    }

    @Test
    public void testChangeAddress() {
        Address newAddress = new Address("Canada", "Toronto", "456 Queen St", "M5H 2N2");
        customer.changeAddress(newAddress);

        assertEquals(newAddress, customer.getAddress());
    }

    @Test
    public void testAddOrder() {
        Order order = new Order(UUID.randomUUID(), customer, address);
        customer.addOrder(order);

        assertEquals(1, customer.getOrders().size());
        assertTrue(customer.getOrders().contains(order));
    }

    @Test
    public void testHasActiveOrders_WithNoActiveOrders() {
        assertFalse(customer.hasActiveOrders());
    }

    @Test
    public void testHasActiveOrders_WithActiveOrders() {
        Order order = new Order(UUID.randomUUID(), customer, address);
        order.changeStatus(OrderStatus.NEW);
        customer.addOrder(order);

        assertTrue(customer.hasActiveOrders());
    }

    @Test
    public void testHasActiveOrders_WithConfirmedOrders() {
        Order order = new Order(UUID.randomUUID(), customer, address);
        order.changeStatus(OrderStatus.CONFIRMED);
        customer.addOrder(order);

        assertTrue(customer.hasActiveOrders());
    }

    @Test
    public void testHasActiveOrders_WithCompletedOrders() {
        Order order = new Order(UUID.randomUUID(), customer, address);
        order.changeStatus(OrderStatus.DELIVERED);
        customer.addOrder(order);

        assertFalse(customer.hasActiveOrders());
    }
}
