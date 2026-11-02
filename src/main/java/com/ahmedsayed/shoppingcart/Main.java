package com.ahmedsayed.shoppingcart;

import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp(
                ProductCatalog.createDefault(),
                new ShoppingCart(),
                new Scanner(System.in),
                System.out
        );
        app.start();
    }
}
