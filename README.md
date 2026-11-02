# Shopping Cart Console

A small Java console application for browsing products, managing a cart, and checking out against available stock.

## Requirements

- Java 21 or later
- Maven 3.9 or later

## Run

```bash
mvn compile exec:java
```

## Test

```bash
mvn test
```

## Features

- Lists a seeded product catalog
- Adds, updates, and removes cart items
- Prevents invalid quantities and purchases above available stock
- Calculates prices with `BigDecimal`
- Deducts stock only after a successful checkout

## Project layout

```
src/main/java/com/ahmedsayed/shoppingcart  Application source
src/test/java/com/ahmedsayed/shoppingcart  Unit tests
```
