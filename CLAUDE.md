# SDUI Backend — Architecture Guide

## Overview

This is a **Server Driven UI (SDUI)** backend. Clients (mobile/web) request a screen by name and receive a list of UI components to render dynamically. The server controls what is shown, without requiring a client-side release.

## Layer Responsibilities

```
ScreenController  ─►  ScreenService  ─►  ScreenRepository
     │                     │                    │
 HTTP + routing       Business logic       Data access
 Error handling       NotFoundException    In-memory map
 JSON responses

ProductController ─►  ProductService  ─►  ProductRepository
     │                     │                    │
 HTTP + routing       Business logic       In-memory map
 Error handling       NotFoundException    Product data
 JSON responses
```

### `controller/ScreenController.kt`
- Exposes `GET /api/v1/screen/{screenName}`
- Exposes `GET /api/v1/product/{productId}` (via `ProductController`)
- Returns `ScreenResponse` wrapped in `ResponseEntity`
- `GlobalExceptionHandler` translates `ScreenNotFoundException` and `ProductNotFoundException` → HTTP 404

### `service/ScreenService.kt`
- Fetches components from the repository
- Throws `ScreenNotFoundException` if screen doesn't exist

### `service/ProductService.kt`
- Builds the product detail screen by fetching a `Product` from `ProductRepository`
- Composes a `ScreenResponse` with `ProductDetailComponent` + action buttons
- Throws `ProductNotFoundException` if product doesn't exist

### `repository/ScreenRepository.kt`
- Holds an in-memory `Map<String, List<Component>>`
- Currently has three sample screens: `home`, `product_list`, `checkout`
- `product_list` cards include an `action` field (e.g. `navigate://product/p1`) for navigation
- Replace with a database-backed implementation when needed

### `repository/ProductRepository.kt`
- Holds an in-memory `Map<String, Product>` with product detail data
- Currently has three sample products: `p1`, `p2`, `p3`
- Replace with a database-backed implementation when needed

## Component Model (`model/Component.kt`)

All components extend the `Component` sealed class. Jackson uses `@JsonTypeInfo` to serialize the `type` field automatically.

| Type             | Fields                                                                            |
|------------------|-----------------------------------------------------------------------------------|
| `banner`         | `imageUrl`, `title`, `subtitle?`                                                  |
| `section_title`  | `title`, `subtitle?`                                                              |
| `product_card`   | `productId`, `name`, `price`, `imageUrl`, `badge?`, `action?`                    |
| `product_detail` | `productId`, `name`, `price`, `imageUrl`, `description`, `rating?`, `badge?`     |
| `button`         | `label`, `action`, `style` (PRIMARY \| SECONDARY)                                |

> `product_card.action` uses the `navigate://product/{productId}` scheme so the client knows to call `GET /api/v1/product/{productId}` when the card is tapped.

## How to Add a New Component

1. **Add a data class** in `model/Component.kt` extending `Component`
2. **Register it** in the `@JsonSubTypes` annotation on `Component`
3. **Add sample data** in `ScreenRepository.kt`

Example:
```kotlin
// 1. model/Component.kt
data class CarouselComponent(
    val type: String = "carousel",
    val items: List<String>,
) : Component()

// 2. @JsonSubTypes — add:
JsonSubTypes.Type(value = CarouselComponent::class, name = "carousel"),
```

## API Contract

### Screen endpoint

**Request:**
```
GET /api/v1/screen/{screenName}
```

**Success (200):**
```json
{
  "screenName": "product_list",
  "components": [
    { "type": "section_title", "title": "All Products", "subtitle": "Browse our full catalog" },
    { "type": "product_card", "productId": "p1", "name": "Running Sneakers Pro", "price": 89.99, "imageUrl": "...", "badge": "NEW", "action": "navigate://product/p1" },
    { "type": "button", "label": "Load More", "action": "load_more://products", "style": "SECONDARY" }
  ]
}
```

**Not Found (404):**
```json
{ "error": "Screen not found: unknown_screen" }
```

---

### Product detail endpoint

**Request:**
```
GET /api/v1/product/{productId}
```

**Success (200):**
```json
{
  "screenName": "product_detail_p1",
  "components": [
    {
      "type": "product_detail",
      "productId": "p1",
      "name": "Running Sneakers Pro",
      "price": 89.99,
      "imageUrl": "...",
      "description": "High-performance running sneakers...",
      "rating": 4.7,
      "badge": "NEW"
    },
    { "type": "button", "label": "Add to Cart", "action": "action://add_to_cart/p1", "style": "PRIMARY" },
    { "type": "button", "label": "Back to Products", "action": "navigate://product_list", "style": "SECONDARY" }
  ]
}
```

**Not Found (404):**
```json
{ "error": "Product not found: unknown_id" }
```

---

## Navigation Flow

```
home screen
  └─► product_list  (action: "navigate://products")
        └─► product detail  (action: "navigate://product/{productId}")
              ├─► checkout  (action: "action://add_to_cart/{productId}")
              └─► product_list  (action: "navigate://product_list")
```

## Technology Stack

- **Spring Boot 3.2** — web framework
- **Kotlin 1.9** — language
- **Gradle 8.5** — build tool
- **Jackson** — JSON serialization with `jackson-module-kotlin`
- **Java 17** — runtime
