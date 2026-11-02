
import java.math.BigDecimal;

public class Product {

    private final int id;
    private final String name;
    private final BigDecimal unitPrice;
    private int availableStock;

    public Product(int id, String name,
                   BigDecimal unitPrice, int availableStock) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid Product ID");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid Product Name");
        }

        if (unitPrice == null || unitPrice.signum() <= 0) {
            throw new IllegalArgumentException("Invalid Price");
        }

        if (availableStock < 0) {
            throw new IllegalArgumentException("Invalid Stock");
        }

        this.id = id;
        this.name = name;
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

    public void decreaseStock(int soldQuantity) {
        if (soldQuantity <= 0) {
            throw new IllegalArgumentException("Sold quantity must be positive!");
        }

        if (soldQuantity > availableStock) {
            throw new IllegalArgumentException("Insufficient stock for product: " + name);
        }

        availableStock = Math.subtractExact(availableStock, soldQuantity);

    }

}
