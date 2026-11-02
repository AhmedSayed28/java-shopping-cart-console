package com.ahmedsayed.shoppingcart;

import java.math.BigDecimal;
import java.util.Objects;

public final class Product {
    private final int id;
    private final String name;
    private final BigDecimal unitPrice;
    private int availableStock;

    public Product(int id, String name, BigDecimal unitPrice, int availableStock) {
        if (id <= 0) {
            throw new IllegalArgumentException("Product ID must be positive.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required.");
        }
        if (unitPrice == null || unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Unit price must be positive.");
        }
        if (availableStock < 0) {
            throw new IllegalArgumentException("Available stock cannot be negative.");
        }

        this.id = id;
        this.name = name.trim();
        this.unitPrice = unitPrice;
        this.availableStock = availableStock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (quantity > availableStock) {
            throw new IllegalArgumentException("Insufficient stock for " + name + ".");
        }

        availableStock -= quantity;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Product product)) {
            return false;
        }
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
