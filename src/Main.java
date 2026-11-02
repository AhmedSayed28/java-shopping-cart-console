void main() {

    ProductCatalog catalog =
            ProductCatalog.createDefault();

    ShoppingCart cart = new ShoppingCart();

    ConsoleApp app = new ConsoleApp(catalog, cart);

    app.start();
}
