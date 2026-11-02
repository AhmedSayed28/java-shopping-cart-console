import java.math.BigDecimal;

public class CartItem {
    // increaseQuantity
    // calculateSubtotal

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {

        if (product == null)
            throw new IllegalArgumentException("Product is Required");

        if (quantity <= 0)
            throw new IllegalArgumentException("Invalid Quantity -> " + " Quantity must be positive");

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    void increaseQuantity(int additionalQuantity){
        if (additionalQuantity <= 0){
            throw new IllegalArgumentException("Invalid quantity");
        }

        this.quantity = Math.addExact(this.quantity , additionalQuantity);
    }


    void updateQuantity(int newQuantity) {

        if (newQuantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero!"
            );
        }

        this.quantity = newQuantity;
    }


    public BigDecimal calculateSubtotal(){
        return product.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
