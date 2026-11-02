package com.ahmedsayed.shoppingcart;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public final class ConsoleApp {
    private final ProductCatalog catalog;
    private final ShoppingCart cart;
    private final Scanner scanner;
    private final PrintStream output;

    public ConsoleApp(ProductCatalog catalog, ShoppingCart cart, Scanner scanner, PrintStream output) {
        this.catalog = catalog;
        this.cart = cart;
        this.scanner = scanner;
        this.output = output;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1" -> printProducts();
                case "2" -> addProductToCart();
                case "3" -> printCart();
                case "4" -> updateProductQuantity();
                case "5" -> removeProductFromCart();
                case "6" -> checkout();
                case "7" -> {
                    output.println("Goodbye!");
                    running = false;
                }
                default -> output.println("Please choose an option from 1 to 7.");
            }
        }
    }

    private void printMenu() {
        output.print("""

                ===== MAIN MENU =====
                1. View Products
                2. Add Product to Cart
                3. View Cart
                4. Update Quantity
                5. Remove Product
                6. Checkout
                7. Exit
                Choose an option: """);
    }

    private void printProducts() {
        output.printf("%-8s %-15s %-15s %-10s%n", "ID", "Name", "Unit Price", "Stock");
        for (Product product : catalog.getAllProducts()) {
            output.printf("%-8d %-15s %-15s %-10d%n", product.getId(), product.getName(), product.getUnitPrice(), product.getAvailableStock());
        }
    }

    private void addProductToCart() {
        Integer productId = readInteger("Enter product ID: ");
        Integer quantity = readInteger("Enter quantity: ");
        if (productId == null || quantity == null) {
            return;
        }

        Product product = catalog.findById(productId).orElse(null);
        if (product == null) {
            output.println("Product not found.");
            return;
        }

        try {
            cart.addProduct(product, quantity);
            output.println("Product added to cart.");
        } catch (IllegalArgumentException exception) {
            output.println(exception.getMessage());
        }
    }

    private void updateProductQuantity() {
        Integer productId = readInteger("Enter product ID: ");
        Integer quantity = readInteger("Enter new quantity: ");
        if (productId == null || quantity == null) {
            return;
        }

        try {
            cart.updateProductQuantity(productId, quantity);
            output.println("Cart quantity updated.");
        } catch (IllegalArgumentException exception) {
            output.println(exception.getMessage());
        }
    }

    private void removeProductFromCart() {
        Integer productId = readInteger("Enter product ID to remove: ");
        if (productId == null) {
            return;
        }

        try {
            cart.removeProduct(productId);
            output.println("Product removed from cart.");
        } catch (IllegalArgumentException exception) {
            output.println(exception.getMessage());
        }
    }

    private void printCart() {
        List<CartItem> cartItems = cart.getItems();
        if (cartItems.isEmpty()) {
            output.println("Your cart is empty.");
            return;
        }

        output.println("\n===== YOUR SHOPPING CART =====");
        output.printf("%-15s %-15s %-10s %-15s%n", "Product", "Unit Price", "Quantity", "Subtotal");
        output.println("-".repeat(60));
        for (CartItem item : cartItems) {
            output.printf("%-15s %-15s %-10d %-15s%n", item.getProduct().getName(), item.getProduct().getUnitPrice(), item.getQuantity(), item.calculateSubtotal());
        }
        output.println("-".repeat(60));
        output.printf("%-42s %s EGP%n", "TOTAL:", cart.calculateTotal());
    }

    private void checkout() {
        try {
            BigDecimal total = cart.checkout();
            output.printf("Checkout complete. Total paid: %s EGP%n", total);
        } catch (IllegalStateException | IllegalArgumentException exception) {
            output.println("Checkout failed: " + exception.getMessage());
        }
    }

    private Integer readInteger(String prompt) {
        output.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException exception) {
            output.println("Please enter a whole number.");
            return null;
        }
    }
}
