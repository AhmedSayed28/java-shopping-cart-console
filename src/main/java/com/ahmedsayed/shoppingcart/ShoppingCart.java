package com.ahmedsayed.shoppingcart;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ShoppingCart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {
        Objects.requireNonNull(product, "Product is required.");
        validatePositiveQuantity(quantity);

        CartItem existingItem = items.get(product.getId());
        int currentQuantity = existingItem == null ? 0 : existingItem.getQuantity();
        Product cartProduct = existingItem == null ? product : existingItem.getProduct();
        int totalQuantity = Math.addExact(currentQuantity, quantity);

        ensureInStock(cartProduct, totalQuantity);

        if (existingItem == null) {
            items.put(product.getId(), new CartItem(product, quantity));
        } else {
            existingItem.increaseQuantity(quantity);
        }
    }

    public void updateProductQuantity(int productId, int quantity) {
        validatePositiveQuantity(quantity);

        CartItem item = items.get(productId);
        if (item == null) {
            throw new IllegalArgumentException("Product is not in the cart.");
        }

        ensureInStock(item.getProduct(), quantity);
        item.updateQuantity(quantity);
    }

    public void removeProduct(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive.");
        }
        if (items.remove(productId) == null) {
            throw new IllegalArgumentException("Product is not in the cart.");
        }
    }

    public List<CartItem> getItems() {
        return List.copyOf(items.values());
    }

    public BigDecimal calculateTotal() {
        return items.values().stream()
                .map(CartItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal checkout() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot check out an empty cart.");
        }

        for (CartItem item : items.values()) {
            ensureInStock(item.getProduct(), item.getQuantity());
        }

        BigDecimal total = calculateTotal();
        for (CartItem item : items.values()) {
            item.getProduct().decreaseStock(item.getQuantity());
        }
        items.clear();
        return total;
    }

    private void validatePositiveQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
    }

    private void ensureInStock(Product product, int quantity) {
        if (quantity > product.getAvailableStock()) {
            throw new IllegalArgumentException(
                    "Only " + product.getAvailableStock() + " unit(s) of " + product.getName() + " are available."
            );
        }
    }
}
