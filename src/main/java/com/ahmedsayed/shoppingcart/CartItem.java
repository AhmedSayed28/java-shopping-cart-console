package com.ahmedsayed.shoppingcart;

import java.math.BigDecimal;
import java.util.Objects;

public final class CartItem {
    private final Product product;
    private int quantity;

    CartItem(Product product, int quantity) {
        this.product = Objects.requireNonNull(product, "Product is required.");
        updateQuantity(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal calculateSubtotal() {
        return product.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }

    void increaseQuantity(int additionalQuantity) {
        if (additionalQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        quantity = Math.addExact(quantity, additionalQuantity);
    }

    void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        quantity = newQuantity;
    }
}
