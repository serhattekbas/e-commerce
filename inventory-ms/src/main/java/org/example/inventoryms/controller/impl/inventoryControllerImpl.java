package org.example.inventoryms.controller.impl;


import lombok.AllArgsConstructor;
import org.example.inventoryms.controller.IInevntoryController;
import org.example.inventoryms.model.inventory;
import org.example.inventoryms.service.IInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/inventory")
@RestController
@AllArgsConstructor

public class inventoryControllerImpl implements IInevntoryController {

    private final IInventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<List<inventory>> createInventory(@RequestBody List<inventory> inventories) {

        List<inventory> createdInventories =
                inventoryService.createInventory(inventories);

        return ResponseEntity.ok(createdInventories);
    }

    @GetMapping("list")
    public ResponseEntity<List<inventory>> listInventory() {
        List<inventory> list = inventoryService.listInventory();
        return ResponseEntity.ok(list);
    }

}
