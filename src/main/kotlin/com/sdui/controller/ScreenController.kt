package com.sdui.controller

import com.sdui.model.ScreenResponse
import com.sdui.service.ProductNotFoundException
import com.sdui.service.ProductService
import com.sdui.service.ScreenNotFoundException
import com.sdui.service.ScreenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ScreenController(private val service: ScreenService) {

    @GetMapping("/screen/{screenName}")
    fun getScreen(@PathVariable screenName: String): ResponseEntity<ScreenResponse> {
        val screen = service.getScreen(screenName)
        return ResponseEntity.ok(screen)
    }
}

@RestController
@RequestMapping("/api/v1")
class ProductController(private val service: ProductService) {

    @GetMapping("/product/{productId}")
    fun getProductScreen(@PathVariable productId: String): ResponseEntity<ScreenResponse> {
        val screen = service.getProductScreen(productId)
        return ResponseEntity.ok(screen)
    }
}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ScreenNotFoundException::class)
    fun handleScreenNotFound(ex: ScreenNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(404).body(ErrorResponse(error = ex.message ?: "Screen not found"))

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(404).body(ErrorResponse(error = ex.message ?: "Product not found"))
}

data class ErrorResponse(val error: String)
