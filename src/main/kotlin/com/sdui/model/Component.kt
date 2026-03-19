package com.sdui.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = BannerComponent::class, name = "banner"),
    JsonSubTypes.Type(value = SectionTitleComponent::class, name = "section_title"),
    JsonSubTypes.Type(value = ProductCardComponent::class, name = "product_card"),
    JsonSubTypes.Type(value = ProductDetailComponent::class, name = "product_detail"),
    JsonSubTypes.Type(value = ButtonComponent::class, name = "button"),
)
sealed class Component

data class BannerComponent(
    val type: String = "banner",
    val imageUrl: String,
    val title: String,
    val subtitle: String? = null,
) : Component()

data class SectionTitleComponent(
    val type: String = "section_title",
    val title: String,
    val subtitle: String? = null,
) : Component()

data class ProductCardComponent(
    val type: String = "product_card",
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val badge: String? = null,
    val action: String? = null,
) : Component()

data class ProductDetailComponent(
    val type: String = "product_detail",
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val rating: Double? = null,
    val badge: String? = null,
) : Component()

data class ButtonComponent(
    val type: String = "button",
    val label: String,
    val action: String,
    val style: ButtonStyle = ButtonStyle.PRIMARY,
) : Component()

enum class ButtonStyle { PRIMARY, SECONDARY }
