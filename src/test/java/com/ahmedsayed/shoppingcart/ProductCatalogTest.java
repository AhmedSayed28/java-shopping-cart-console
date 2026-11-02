package com.ahmedsayed.shoppingcart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductCatalogTest {
    @Test
    void findsAProductByItsId() {
        ProductCatalog catalog = new ProductCatalog();
        Product tea = new Product(7, "Tea", new BigDecimal("55.00"), 4);
        catalog.addProduct(tea);

        assertEquals(tea, catalog.findById(7).orElseThrow());
        assertTrue(catalog.findById(99).isEmpty());
    }

    @Test
    void doesNotAllowDuplicateProductIds() {
        ProductCatalog catalog = new ProductCatalog();
        catalog.addProduct(new Product(7, "Tea", new BigDecimal("55.00"), 4));

        assertThrows(
                IllegalArgumentException.class,
                () -> catalog.addProduct(new Product(7, "Coffee", new BigDecimal("65.00"), 2))
        );
    }
}
