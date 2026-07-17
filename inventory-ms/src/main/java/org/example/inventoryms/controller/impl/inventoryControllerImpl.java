package org.example.inventoryms.controller.impl;


import lombok.AllArgsConstructor;
import org.example.inventoryms.controller.IInevntoryController;
import org.example.inventoryms.model.inventory;
import org.example.inventoryms.service.impl.inventoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/inventory")
@RestController
@AllArgsConstructor

public class inventoryControllerImpl implements IInevntoryController {

    private final inventoryServiceImpl inventoryServiceImpl ;

    @PostMapping("/create")
    public ResponseEntity<inventory> createInventory(@RequestBody inventory inventory) {
         inventory createdInventory = inventoryServiceImpl.createInventory(inventory);
         return ResponseEntity.ok(createdInventory);
    }

    @GetMapping("list")
    public ResponseEntity<List<inventory>> listInventory() {
        List<inventory> list = inventoryServiceImpl.listInventory();
        return ResponseEntity.ok(list);
    }




}
