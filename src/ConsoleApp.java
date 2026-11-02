
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    private final ProductCatalog catalog;
    private final ShoppingCart cart;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApp(ProductCatalog catalog, ShoppingCart cart) {
        this.catalog = catalog;
        this.cart = cart;
    }

    public void start() {

        while (true) {
            printMenu();

            String option = scanner.nextLine().trim();

            switch (option) {

                case "1" -> printProducts();

                case "2" -> addProductToCart();

                case "3" -> printCart();

                case "4" -> updateProductQuantity();

                case "5" -> removeProductFromCart();

                case "6" -> checkout();

                case "7" -> {
                    System.out.println("Goodbye!");
                    return;
                }

                default -> System.out.println("Invalid Option");
            }
        }
    }

    private void printMenu() {

        System.out.println("""
                
                ===== MAIN MENU =====
                1. View Products
                2. Add Product to Cart
                3. View Cart
                4. Update Quantity
                5. Remove Product
                6. Checkout
                7. Exit
                
                Choose an option:
                """);
    }

    private void printProducts() {

        List<Product> products = catalog.getAllProducts();

        System.out.printf(
                "%-8s %-15s %-15s %-15s%n",
                "ID", "Name", "Unit Price", "Stock"
        );

        for (Product product : products) {

            System.out.printf(
                    "%-8d %-15s %-15s %-15d%n",
                    product.getId(),
                    product.getName(),
                    product.getUnitPrice(),
                    product.getAvailableStock()
            );
        }
    }

    private void addProductToCart() {

        Integer productId = readInteger("Enter Product ID: ");
        if (productId == null) return;

        Integer quantity = readInteger("Enter Quantity: ");
        if (quantity == null) return;

        Product product = catalog.findById(productId);

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        try {
            cart.addProduct(product, quantity);
            System.out.println("Product added successfully!");

        } catch (IllegalArgumentException |
                 UnsupportedOperationException ex) {

            System.out.println(ex.getMessage());
        }
    }

    private Integer readInteger(String message) {

        System.out.print(message);

        try {
            return Integer.parseInt(scanner.nextLine().trim());

        } catch (NumberFormatException ex) {
            System.out.println("Please enter a valid number!");
            return null;
        }
    }


    private void printCart() {

        List<CartItem> cartItems = cart.getItems();

        // Check if cart is empty
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }

        System.out.println("\n===== YOUR SHOPPING CART =====");

        // Print table headers
        System.out.printf(
                "%-15s %-15s %-10s %-15s%n",
                "Product", "Unit Price", "Quantity", "Subtotal"
        );

        System.out.println("-".repeat(60));

        // Print cart items
        for (CartItem item : cartItems) {

            Product product = item.getProduct();

            System.out.printf(
                    "%-15s %-15s %-10d %-15s%n",
                    product.getName(),
                    product.getUnitPrice(),
                    item.getQuantity(),
                    item.calculateSubtotal()
            );
        }

        System.out.println("-".repeat(60));

        // Print final cart total
        System.out.printf(
                "%-42s %s EGP%n",
                "TOTAL:",
                cart.calculateTotal()
        );
    }


    private void updateProductQuantity() {

        Integer productId = readInteger("Enter Product ID: ");
        if (productId == null) return;

        Integer newQuantity = readInteger("Enter New Quantity: ");
        if (newQuantity == null) return;

        try {
            cart.updateProductQuantity(productId, newQuantity);

            System.out.println(
                    "Product quantity updated successfully!"
            );

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private void removeProductFromCart() {

        Integer productId = readInteger(
                "Enter Product ID to remove: "
        );

        if (productId == null) {
            return;
        }

        try {
            cart.removeProduct(productId);

            System.out.println(
                    "Product removed successfully!"
            );

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private void checkout() {

        try {
            BigDecimal finalTotal = cart.checkout();

            System.out.println("\n===== RECEIPT =====");
            System.out.println("Checkout completed successfully!");

            System.out.println(
                    "Total Paid: " + finalTotal + " EGP"
            );

        } catch (IllegalArgumentException ex) {
            System.out.println(
                    "Checkout failed: " + ex.getMessage()
            );
        }
    }



}
