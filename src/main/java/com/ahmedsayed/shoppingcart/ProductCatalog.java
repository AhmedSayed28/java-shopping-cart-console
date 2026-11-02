package com.ahmedsayed.shoppingcart;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class ProductCatalog {
    private final Map<Integer, Product> products = new LinkedHashMap<>();

    public static ProductCatalog createDefault() {
        ProductCatalog catalog = new ProductCatalog();
        catalog.addProduct(new Product(101, "Milk", new BigDecimal("35.00"), 10));
        catalog.addProduct(new Product(102, "Bread", new BigDecimal("15.00"), 20));
        catalog.addProduct(new Product(103, "Cheese", new BigDecimal("80.00"), 5));
        catalog.addProduct(new Product(104, "Rice", new BigDecimal("60.00"), 8));
        catalog.addProduct(new Product(105, "Sugar", new BigDecimal("40.00"), 15));
        return catalog;
    }

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product is required.");
        if (products.putIfAbsent(product.getId(), product) != null) {
            throw new IllegalArgumentException("A product with this ID already exists.");
        }
    }

    public Optional<Product> findById(int productId) {
        return Optional.ofNullable(products.get(productId));
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }
}
