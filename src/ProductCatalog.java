import java.math.BigDecimal;
import java.util.*;

public class ProductCatalog {

    private final Map<Integer, Product> products =
            new LinkedHashMap<>();

    public static ProductCatalog createDefault() {

        ProductCatalog catalog = new ProductCatalog();

        catalog.addProduct(new Product(
                101, "Milk", new BigDecimal("35"), 10));

        catalog.addProduct(new Product(
                102, "Bread", new BigDecimal("15"), 20));

        catalog.addProduct(new Product(
                103, "Cheese", new BigDecimal("80"), 5));

        catalog.addProduct(new Product(
                104, "Rice", new BigDecimal("60"), 8));

        catalog.addProduct(new Product(
                105, "Sugar", new BigDecimal("40"), 15));

        return catalog;
    }

    private void addProduct(Product product) {
        if (products.putIfAbsent(product.getId(), product) != null) {
            throw new IllegalArgumentException("Duplicate Product ID");
        }
    }

    public Product findById(int productId) {
        return products.get(productId);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }
}
