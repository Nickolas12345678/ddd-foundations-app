package com.nickolas.ddd;

import com.nickolas.ddd.entities.Customer;
import com.nickolas.ddd.entities.Order;
import com.nickolas.ddd.valueobjects.*;

import java.math.BigDecimal;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UUID customerId = UUID.randomUUID();
        Name customerName = new Name("John", "Doe");
        Email customerEmail = new Email("john.doe@example.com");
        Address customerAddress = new Address("USA", "New York", "5th Avenue", "10001");

        Customer customer = new Customer(customerId, customerName, customerEmail, customerAddress);
        System.out.println("Створено клієнта: " + customer.getName().getFullName());

        UUID orderId = UUID.randomUUID();
        Address shippingAddress = new Address("USA", "Los Angeles", "Sunset Blvd", "90001");
        Order order = new Order(orderId, customer, shippingAddress);
        System.out.println("Створено замовлення для клієнта: " + customer.getName().getFullName());

        Money itemPrice = new Money("USD", BigDecimal.valueOf(49.99));
        Dimensions itemDimensions = new Dimensions(10, 5, 2);
        OrderItemDetails item1 = new OrderItemDetails("Laptop", 1, itemPrice);
        order.addItem(item1);
        System.out.println("Додано " + item1.getQuantity() + " одиниць товару: " + item1.getName());

        Money itemPrice2 = new Money("USD", BigDecimal.valueOf(19.99));
        OrderItemDetails item2 = new OrderItemDetails("Mouse", 2, itemPrice2);
        order.addItem(item2);
        System.out.println("Додано " + item2.getQuantity() + " одиниць товару: " + item2.getName());

        System.out.println("Загальна сума замовлення: " + order.getTotalPrice().toDisplayFormat());

        customer.addOrder(order);
        System.out.println("Замовлення додано до списку клієнта.");

        System.out.println("Чи є у клієнта активні замовлення? " + customer.hasActiveOrders());

        order.changeStatus(OrderStatus.CONFIRMED);
        System.out.println("Статус замовлення змінено на: " + order.getStatus());

        try {
            order.changeStatus(OrderStatus.SHIPPED);
            order.changeShippingAddress(new Address("Canada", "Toronto", "Queen St", "M5H 2N2"));
        } catch (IllegalStateException e) {
            System.out.println("Помилка зміни адреси: " + e.getMessage());
        }

        System.out.println("Кінцевий статус замовлення: " + order.getStatus());
        System.out.println("Клієнтська адреса доставки: " + order.getShippingAddress().toDisplayFormat());
    }
}

