package com.ahmedsayed.shoppingcart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {
    private final Product milk = new Product(1, "Milk", new BigDecimal("35.00"), 10);

    @Test
    void addsTheSameProductAsOneCartItem() {
        ShoppingCart cart = new ShoppingCart();

        cart.addProduct(milk, 2);
        cart.addProduct(milk, 3);

        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().getFirst().getQuantity());
        assertEquals(new BigDecimal("175.00"), cart.calculateTotal());
    }

    @Test
    void rejectsQuantityAboveAvailableStock() {
        ShoppingCart cart = new ShoppingCart();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cart.addProduct(milk, 11)
        );

        assertEquals("Only 10 unit(s) of Milk are available.", exception.getMessage());
    }

    @Test
    void checkoutDeductsStockAndEmptiesTheCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(milk, 2);

        BigDecimal total = cart.checkout();

        assertEquals(new BigDecimal("70.00"), total);
        assertEquals(8, milk.getAvailableStock());
        assertFalse(cart.getItems().iterator().hasNext());
    }

    @Test
    void updateReportsWhenProductIsNotInCart() {
        ShoppingCart cart = new ShoppingCart();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cart.updateProductQuantity(1, 2)
        );

        assertEquals("Product is not in the cart.", exception.getMessage());
    }
}
