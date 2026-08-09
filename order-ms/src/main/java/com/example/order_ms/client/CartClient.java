package com.example.order_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart-ms", url = "http://localhost:8081") // Cart Service'in adresi
public interface CartClient {

    @GetMapping("/api/shoppingcart/items/{userId}")
    List<CartItemResponse> getCartItems(@PathVariable Long userId);

    @DeleteMapping("/api/shoppingcart/clear/{userId}")
    void clearCart(@PathVariable Long userId);

    @lombok.Data
    public static class CartResponse {
        private Long id;
        private Long userId;
        private List<CartItemResponse> items;
        private double totalAmount;
    }

    @lombok.Data
    public static class CartItemResponse {
        private Long productId;
        private Integer quantity;
        private double price;
        private Double totalPrice;
    }
}
