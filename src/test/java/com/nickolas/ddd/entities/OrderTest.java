package com.nickolas.ddd.entities;

import com.nickolas.ddd.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private Customer createCustomer() {
        Name name = new Name("John", "Doe");
        Email email = new Email("johndoe@example.com");
        Address address = new Address("USA", "Springfield", "742 Evergreen Terrace", "12345");
        return new Customer(UUID.randomUUID(), name, email, address);
    }

    private Order createOrder(Customer customer) {
        UUID orderId = UUID.randomUUID();
        Address address = customer.getAddress(); // використовуємо адресу з клієнта
        return new Order(orderId, customer, address);
    }

    @Test
    void testOrderCreation() {
        Customer customer = createCustomer();
        Order order = createOrder(customer);

        assertNotNull(order);
        assertEquals(order.getId(), order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(customer.getAddress(), order.getShippingAddress());
        assertEquals(new Money("USD", BigDecimal.ZERO), order.getTotalPrice());
    }

    @Test
    void testAddItem() {
        Customer customer = createCustomer();
        Order order = createOrder(customer);

        OrderItemDetails item = new OrderItemDetails("Item 1", 2, new Money("USD", BigDecimal.valueOf(10)));
        order.addItem(item);

        assertEquals(1, order.getItems().size());
        assertEquals(new Money("USD", BigDecimal.valueOf(20)), order.getTotalPrice());
    }

    @Test
    void testChangeStatus() {
        Customer customer = createCustomer();
        Order order = createOrder(customer);

        assertEquals(OrderStatus.NEW, order.getStatus());

        order.changeStatus(OrderStatus.CONFIRMED);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());

        order.changeStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getStatus());

        order.changeStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());

        assertThrows(IllegalStateException.class, () -> order.changeStatus(OrderStatus.NEW));
        assertThrows(IllegalStateException.class, () -> order.changeStatus(OrderStatus.CONFIRMED));
    }

    @Test
    void testRecalculateTotalPrice() {
        Customer customer = createCustomer();
        Order order = createOrder(customer);

        OrderItemDetails item1 = new OrderItemDetails("Item 1", 2, new Money("USD", BigDecimal.valueOf(10)));
        OrderItemDetails item2 = new OrderItemDetails("Item 2", 1, new Money("USD", BigDecimal.valueOf(15)));
        order.addItem(item1);
        order.addItem(item2);

        assertEquals(new Money("USD", BigDecimal.valueOf(35)), order.getTotalPrice());
    }

    @Test
    void testChangeShippingAddress() {
        Customer customer = createCustomer();
        Order order = createOrder(customer);

        Address newAddress = new Address("UK", "London", "10 Downing Street", "SW1A 2AA");
        order.changeShippingAddress(newAddress);
        assertEquals(newAddress, order.getShippingAddress());

        order.changeStatus(OrderStatus.SHIPPED);
        assertThrows(IllegalStateException.class, () -> order.changeShippingAddress(new Address("UK", "London", "10 Downing Street", "SW1A 2AA")));
    }
}
