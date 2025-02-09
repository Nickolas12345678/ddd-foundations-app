package com.nickolas.ddd.entities;

import com.nickolas.ddd.valueobjects.Address;
import com.nickolas.ddd.valueobjects.Money;
import com.nickolas.ddd.valueobjects.OrderItemDetails;
import com.nickolas.ddd.valueobjects.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final Customer customer;
    private final List<OrderItemDetails> items;
    private Money totalPrice;
    private OrderStatus status;
    private Address shippingAddress;

    public Order(UUID id, Customer customer, Address shippingAddress) {
        if (customer == null || shippingAddress == null) {
            throw new IllegalArgumentException("Customer and shipping address cannot be null");
        }
        this.id = id;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.NEW;
        this.shippingAddress = shippingAddress;
        this.totalPrice = new Money("USD", BigDecimal.ZERO);
    }


    public void addItem(OrderItemDetails item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.add(item);
        recalculateTotalPrice();
    }


    public void changeStatus(OrderStatus newStatus) {
        if (newStatus == OrderStatus.NEW && status != OrderStatus.NEW) {
            throw new IllegalStateException("Cannot revert to NEW status.");
        }
        if (status == OrderStatus.SHIPPED && newStatus != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot move from SHIPPED to other statuses.");
        }
        this.status = newStatus;
    }


    private void recalculateTotalPrice() {
        Money newTotal = new Money("USD", BigDecimal.ZERO);
        for (OrderItemDetails item : items) {
            newTotal = newTotal.add(item.calculateTotalPrice());
        }
        this.totalPrice = newTotal;
    }


    public void changeShippingAddress(Address newAddress) {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot change address after shipping or delivery.");
        }
        if (newAddress == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.shippingAddress = newAddress;
    }


    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItemDetails> getItems() {
        return items;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }
}
