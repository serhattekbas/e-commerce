package com.example.order_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "inventory-ms", url = "http://localhost:8080") // Inventory Service'in adresi
public interface InventoryClient {
    @PostMapping("/inventory/decrease")
    void decreaseInventory(@RequestBody List<StockUpdateRequest> requests);

    @PostMapping("/inventory/increase")
    void increaseInventory(@RequestBody List<StockUpdateRequest> requests);
}

class StockUpdateRequest {
    private Long productId;
    private Integer quantity;
    // Getters and setters
}
