package com.example.order_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart-ms", url = "http://localhost:8081") // Cart Service'in adresi
public interface CartClient {
    @GetMapping("/carts/user/{userId}")
    CartResponse getCartByUserId(@PathVariable Long userId);

    @DeleteMapping("/carts/{cartId}/items")
    void clearCart(@PathVariable Long cartId);
}

// --- Ek DTO'lar (Cart Service'den gelmesi gerekenler) ---

class CartResponse {
    private Long id;
    private Long userId;
    private List<CartItemResponse> items;
    private double totalAmount;
    // Getters and setters
}

class CartItemResponse {
    private Long productId;
    private Integer quantity;
    private double price;
    // Getters and setters
}
