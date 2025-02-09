package com.nickolas.ddd.aggregates;

import com.nickolas.ddd.entities.Customer;
import com.nickolas.ddd.valueobjects.OrderItemDetails;
import com.nickolas.ddd.valueobjects.OrderStatus;
import com.nickolas.ddd.valueobjects.ShippingAddress;
import com.nickolas.ddd.valueobjects.Money;
import java.math.BigDecimal;



import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Customer customer;
    private ShippingAddress shippingAddress;
    private List<OrderItemDetails> orderItems;
    private OrderStatus status;
    private double totalPrice;

    public Order(Customer customer, ShippingAddress shippingAddress) {
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.orderItems = new ArrayList<>();
        this.status = OrderStatus.NEW;  // За замовчуванням статус "NEW"
        this.totalPrice = 0.0;
    }


    public void addItem(OrderItemDetails item) {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot modify order after it's shipped");
        }
        this.orderItems.add(item);
        updateTotalPrice();
    }

    public void removeItem(OrderItemDetails item) {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot modify order after it's shipped");
        }
        this.orderItems.remove(item);
        updateTotalPrice();
    }


    private void updateTotalPrice() {
        this.totalPrice = orderItems.stream()
                .map(OrderItemDetails::calculateTotalPrice)
                .map(Money::getAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }


    public void setStatus(OrderStatus status) {
        if (this.status == OrderStatus.SHIPPED && status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot change status once the order is shipped");
        }
        this.status = status;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public ShippingAddress getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddress shippingAddress) { this.shippingAddress = shippingAddress; }

    public List<OrderItemDetails> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDetails> orderItems) { this.orderItems = orderItems; }

    public OrderStatus getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }
}
