package com.nickolas.ddd.valueobjects;

public final class OrderItemDetails {
    private final String productId;
    private final int quantity;
    private final Money price;

    public OrderItemDetails(String productId, int quantity, Money price) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Money calculateTotalPrice() {
        return price.multiply(quantity);
    }

    public String getName() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }
}
