package com.example.order_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-ms", url = "http://localhost:8080")
public interface InventoryClient {

    @PostMapping("/api/inventory/decrease-stock")
    void decreaseStock(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity);

    @PostMapping("/api/inventory/increase-stock")
    void increaseStock(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity);
}

