package org.example.inventoryms.service;

import org.example.inventoryms.model.inventory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

public interface IInventoryService {
    List<inventory> createInventory(List<inventory> inventories);

    List<inventory> listInventory();

    void decreaseStock(Long productId, Integer quantity);

    void increaseStock(Long productId, Integer quantity);

    public ResponseEntity<inventory> updateInventory(Long productId,inventory inventory)
}