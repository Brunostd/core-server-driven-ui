package com.sdui.repository

import com.sdui.model.*
import org.springframework.stereotype.Repository

@Repository
class ScreenRepository {

    private val screens: Map<String, List<Component>> = mapOf(
        "home" to listOf(
            BannerComponent(
                imageUrl = "https://placehold.co/800x300/orange/white?text=Summer+Sale",
                title = "Summer Sale",
                subtitle = "Up to 50% off selected items",
            ),
            SectionTitleComponent(title = "Featured Products"),
            ProductCardComponent(
                productId = "p1",
                name = "Running Sneakers Pro",
                price = 89.99,
                imageUrl = "https://placehold.co/200x200/blue/white?text=Sneakers",
                action = "navigate://product/p1",
                badge = "NEW",
            ),
            ProductCardComponent(
                productId = "p2",
                name = "Classic Leather Watch",
                price = 149.90,
                imageUrl = "https://placehold.co/200x200/brown/white?text=Watch",
                action = "navigate://product/p2",
            ),
            ButtonComponent(
                label = "See All Products",
                action = "navigate://products",
                style = ButtonStyle.PRIMARY,
            ),
        ),
        "product_list" to listOf(
            SectionTitleComponent(title = "All Products", subtitle = "Browse our full catalog"),
            ProductCardComponent(
                productId = "p1",
                name = "Running Sneakers Pro",
                price = 89.99,
                imageUrl = "https://placehold.co/200x200/blue/white?text=Sneakers",
                badge = "NEW",
                action = "navigate://product/p1",
            ),
            ProductCardComponent(
                productId = "p2",
                name = "Classic Leather Watch",
                price = 149.90,
                imageUrl = "https://placehold.co/200x200/brown/white?text=Watch",
                action = "navigate://product/p2",
            ),
            ProductCardComponent(
                productId = "p3",
                name = "Wireless Headphones",
                price = 199.00,
                imageUrl = "https://placehold.co/200x200/gray/white?text=Headphones",
                badge = "SALE",
                action = "navigate://product/p3",
            ),
            ButtonComponent(
                label = "Load More",
                action = "load_more://products",
                style = ButtonStyle.SECONDARY,
            ),
        ),
        "checkout" to listOf(
            SectionTitleComponent(title = "Checkout"),
            BannerComponent(
                imageUrl = "https://placehold.co/800x100/green/white?text=Free+Shipping+Over+%24100",
                title = "Free Shipping",
                subtitle = "On orders over \$100",
            ),
            ButtonComponent(
                label = "Confirm Order",
                action = "action://confirm_order",
                style = ButtonStyle.PRIMARY,
            ),
            ButtonComponent(
                label = "Continue Shopping",
                action = "navigate://home",
                style = ButtonStyle.SECONDARY,
            ),
        ),
    )

    fun findByName(screenName: String): List<Component>? = screens[screenName]
}
