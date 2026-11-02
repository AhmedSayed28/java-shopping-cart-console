
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final Map<Integer, CartItem> items =
            new LinkedHashMap<>();

    public void addProduct(Product product, int requestedQuantity) {

        // Step 1: Validate product and requested quantity
        if (product == null) {
            throw new IllegalArgumentException("Product is required!");
        }

        if (requestedQuantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero!"
            );
        }

        // Step 2: Find existing CartItem using Product ID
        CartItem existingItem = items.get(product.getId());

        // Step 3: Calculate current cart quantity
        int currentQuantity = existingItem == null
                ? 0
                : existingItem.getQuantity();

        // Step 4: Calculate remaining available stock
        int availableStock = existingItem == null
                ? product.getAvailableStock()
                : existingItem.getProduct().getAvailableStock();

        int remainingQuantity = availableStock - currentQuantity;

        if (requestedQuantity > remainingQuantity) {
            throw new IllegalArgumentException(
                    "Insufficient stock. You can add only "
                            + Math.max(0, remainingQuantity)
                            + " more units."
            );
        }

        // Step 5: Validate total quantity against stock
        int totalQuantity = Math.addExact(
                currentQuantity,
                requestedQuantity
        );

        // Step 6: Add new CartItem OR increase existing quantity
        if (existingItem != null) {
            existingItem.increaseQuantity(requestedQuantity);
        } else {
            CartItem newItem = new CartItem(
                    product,
                    requestedQuantity
            );

            items.put(product.getId(), newItem);
        }
    }


    public List<CartItem> getItems(){
        return List.copyOf(items.values());
    }


    public BigDecimal calculateTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items.values()) {

            BigDecimal subtotal = item.calculateSubtotal();

            total = total.add(subtotal);
        }

        return total;
    }


    public void updateProductQuantity(
            int productId,
            int newQuantity
    ) {

        // TODO 1:
        // Validate that newQuantity is greater than zero

        if (newQuantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero!"
            );
        }

        // TODO 2:
        // Find the existing CartItem using productId

        CartItem existingItem = items.get(productId);

        // TODO 3:
        // Reject if the product is not in the cart
        if (existingItem == null){
            throw new IllegalArgumentException(
                    ""
            );
        }
        // TODO 4:
        // Validate newQuantity against available stock

        if (newQuantity > existingItem.getProduct().getAvailableStock()) {
            throw new IllegalArgumentException(
                    "Insufficient stock. Available stock: "
                            + existingItem.getProduct().getAvailableStock()
            );
        }


        // TODO 5:
        // Update the quantity of the existing CartItem

        existingItem.updateQuantity(newQuantity);
    }



    public void removeProduct(int productId) {

        // Step 1: Validate Product ID
        if (productId <= 0) {
            throw new IllegalArgumentException(
                    "Enter a valid Product ID!"
            );
        }

        // Step 2: Remove the product
        CartItem removedItem = items.remove(productId);

        // Step 3: Validate removal result
        if (removedItem == null) {
            throw new IllegalArgumentException(
                    "Product not found in shopping cart!"
            );
        }
    }

    public BigDecimal checkout() {

        // TODO 1: Reject checkout if cart is empty

        if (items.isEmpty()){
            throw new IllegalArgumentException(" Checkout Rejected - Cart is Empty!");
        }

        // TODO 2: Validate available stock for ALL items
        // Use a for loop without changing stock

        for (CartItem item : items.values()){
            Product product = item.getProduct();

            int requestedQuantity = item.getQuantity();

            int availableStock = product.getAvailableStock();

            if (requestedQuantity > availableStock){
                throw new IllegalArgumentException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }
        }


        // TODO 3: Calculate and save the final total

        BigDecimal total = calculateTotal();

        // TODO 4: Deduct stock for ALL items
        // Use another for loop

        for (CartItem item : items.values()){
            Product product = item.getProduct();

            int requestedQuantity = item.getQuantity();

            product.decreaseStock(requestedQuantity);

        }

        // TODO 5: Clear the cart

        items.clear();

        // TODO 6: Return the final total

        return total;
    }



}
