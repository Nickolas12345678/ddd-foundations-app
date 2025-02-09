package com.nickolas.ddd.entities;

import com.nickolas.ddd.valueobjects.Address;
import com.nickolas.ddd.valueobjects.Email;
import com.nickolas.ddd.valueobjects.Name;
import com.nickolas.ddd.valueobjects.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private Name name;
    private Email email;
    private Address address;
    private final List<Order> orders;


    public Customer(UUID id, Name name, Email email, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.orders = new ArrayList<>();
    }


    public void changeAddress(Address newAddress) {
        if (newAddress == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.address = newAddress;
    }


    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        this.orders.add(order);
    }


    public boolean hasActiveOrders() {
        return orders.stream().anyMatch(order -> order.getStatus() == OrderStatus.NEW ||
                order.getStatus() == OrderStatus.CONFIRMED);
    }


    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
