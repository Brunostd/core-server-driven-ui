package com.sdui.service

import com.sdui.model.*
import com.sdui.repository.ProductRepository
import org.springframework.stereotype.Service

class ProductNotFoundException(productId: String) :
    RuntimeException("Product not found: $productId")

@Service
class ProductService(private val repository: ProductRepository) {

    fun getProductScreen(productId: String): ScreenResponse {
        val product = repository.findById(productId)
            ?: throw ProductNotFoundException(productId)

        return ScreenResponse(
            screenName = "product_detail_$productId",
            components = listOf(
                ProductDetailComponent(
                    productId = product.productId,
                    name = product.name,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    description = product.description,
                    rating = product.rating,
                    badge = product.badge,
                ),
                ButtonComponent(
                    label = "Add to Cart",
                    action = "action://add_to_cart/${product.productId}",
                    style = ButtonStyle.PRIMARY,
                ),
                ButtonComponent(
                    label = "Back to Products",
                    action = "navigate://home",
                    style = ButtonStyle.SECONDARY,
                ),
            ),
        )
    }
}
