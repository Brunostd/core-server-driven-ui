package com.sdui.repository

import org.springframework.stereotype.Repository

data class Product(
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val rating: Double? = null,
    val badge: String? = null,
)

@Repository
class ProductRepository {

    private val products: Map<String, Product> = mapOf(
        "p1" to Product(
            productId = "p1",
            name = "Running Sneakers Pro",
            price = 89.99,
            imageUrl = "https://placehold.co/600x400/blue/white?text=Sneakers",
            description = "High-performance running sneakers with advanced cushioning and breathable mesh upper. Perfect for long-distance runs and everyday training.",
            rating = 4.7,
            badge = "NEW",
        ),
        "p2" to Product(
            productId = "p2",
            name = "Classic Leather Watch",
            price = 149.90,
            imageUrl = "https://placehold.co/600x400/brown/white?text=Watch",
            description = "Elegant classic leather watch with stainless steel case and sapphire crystal glass. Water-resistant up to 50 meters.",
            rating = 4.5,
        ),
        "p3" to Product(
            productId = "p3",
            name = "Wireless Headphones",
            price = 199.00,
            imageUrl = "https://placehold.co/600x400/gray/white?text=Headphones",
            description = "Premium wireless headphones with active noise cancellation and up to 30 hours of battery life. Crystal-clear audio for music and calls.",
            rating = 4.8,
            badge = "SALE",
        ),
    )

    fun findById(productId: String): Product? = products[productId]
}
