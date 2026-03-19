# core-server-driven-ui

Backend for a **Server Driven UI (SDUI)** system built with Spring Boot 3 and Kotlin. The server returns screen definitions as a list of typed UI components, enabling clients to render screens without client-side releases.

## Prerequisites

- Java 17+
- No other installation required (Gradle wrapper is included)

## Running

```bash
./gradlew bootRun
```

On Windows:
```bat
gradlew.bat bootRun
```

The server starts at `http://localhost:8080`.

## API

### Get screen components

```
GET /api/v1/screen/{screenName}
```

**Available screens:** `home`, `product_list`, `checkout`

### Get product detail screen

```
GET /api/v1/product/{productId}
```

**Available products:** `p1`, `p2`, `p3`

### Examples

```bash
# Home screen
curl http://localhost:8080/api/v1/screen/home

# Product list
curl http://localhost:8080/api/v1/screen/product_list

# Product detail
curl http://localhost:8080/api/v1/product/p1
curl http://localhost:8080/api/v1/product/p2
curl http://localhost:8080/api/v1/product/p3

# Checkout
curl http://localhost:8080/api/v1/screen/checkout

# Unknown screen → 404
curl http://localhost:8080/api/v1/screen/nonexistent

# Unknown product → 404
curl http://localhost:8080/api/v1/product/nonexistent
```

### Screen response format

```json
{
  "screenName": "product_list",
  "components": [
    {
      "type": "section_title",
      "title": "All Products",
      "subtitle": "Browse our full catalog"
    },
    {
      "type": "product_card",
      "productId": "p1",
      "name": "Running Sneakers Pro",
      "price": 89.99,
      "imageUrl": "https://...",
      "badge": "NEW",
      "action": "navigate://product/p1"
    },
    {
      "type": "button",
      "label": "Load More",
      "action": "load_more://products",
      "style": "SECONDARY"
    }
  ]
}
```

### Product detail response format

```json
{
  "screenName": "product_detail_p1",
  "components": [
    {
      "type": "product_detail",
      "productId": "p1",
      "name": "Running Sneakers Pro",
      "price": 89.99,
      "imageUrl": "https://...",
      "description": "High-performance running sneakers...",
      "rating": 4.7,
      "badge": "NEW"
    },
    {
      "type": "button",
      "label": "Add to Cart",
      "action": "action://add_to_cart/p1",
      "style": "PRIMARY"
    },
    {
      "type": "button",
      "label": "Back to Products",
      "action": "navigate://product_list",
      "style": "SECONDARY"
    }
  ]
}
```

### Error response (404)

```json
{ "error": "Screen not found: nonexistent" }
{ "error": "Product not found: nonexistent" }
```

## Navigation Flow

```
home
 └─► product_list          (action: "navigate://products")
       └─► product detail  (action: "navigate://product/{productId}")
             ├─► checkout  (action: "action://add_to_cart/{productId}")
             └─► product_list  (action: "navigate://product_list")
```

The `action` field on `product_card` components tells the client which endpoint to call when the card is tapped (`navigate://product/{productId}` → `GET /api/v1/product/{productId}`).

## Supported Component Types

| `type`           | Fields                                                                          |
|------------------|---------------------------------------------------------------------------------|
| `banner`         | `imageUrl`, `title`, `subtitle` (optional)                                     |
| `section_title`  | `title`, `subtitle` (optional)                                                  |
| `product_card`   | `productId`, `name`, `price`, `imageUrl`, `badge` (opt.), `action` (opt.)      |
| `product_detail` | `productId`, `name`, `price`, `imageUrl`, `description`, `rating` (opt.), `badge` (opt.) |
| `button`         | `label`, `action`, `style` (`PRIMARY` or `SECONDARY`)                          |

## Build

```bash
# Build JAR
./gradlew build

# Run tests
./gradlew test

# Run JAR directly
java -jar build/libs/core-server-driven-ui-0.0.1-SNAPSHOT.jar
```

## Architecture

See [CLAUDE.md](CLAUDE.md) for a detailed architecture guide, including how to add new component types.
